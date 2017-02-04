package org.bytedeco.javacpp.caffe_example;

import org.bytedeco.javacpp.FloatPointer;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.bytedeco.javacpp.caffe.*;
import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_imgcodecs.*;
import static org.bytedeco.javacpp.opencv_imgproc.*;

public class CaffeClassifier {

    private final FloatNet net;
    private final Mat mean;
    private final List<String> labels;

    private final FloatBlob inputLayer;
    private final int numChannels;
    private final int height;
    private final int width;
    private final Size inputGeometry;

    public CaffeClassifier(FloatNet net, Mat mean, List<String> labels) {
        this.net = net;
        this.mean = mean;
        this.labels = labels;

        this.inputLayer = net.input_blobs().get(0);
        this.numChannels = inputLayer.channels();
        this.width = inputLayer.width();
        this.height = inputLayer.height();
        this.inputGeometry = new Size(width, height);

        if (numChannels != 1 && numChannels != 3) {
            throw new IllegalStateException("Input layer should have 1 or 3 channels");
        }
    }

    public List<Prediction> classify(String imagePath, int maxResults) {
        Mat image = loadImage(imagePath);

        float[] result = predict(image);
        Prediction[] predictions = new Prediction[result.length];
        Arrays.setAll(predictions, i -> new Prediction(labels.get(i), result[i]));

        int resultSize = Math.min(maxResults, labels.size());
        return Arrays.stream(predictions)
                .sorted()
                .limit(resultSize)
                .collect(Collectors.toList());
    }

    private Mat loadImage(String imagePath) {
        Mat image = imread(imagePath, IMREAD_UNCHANGED);
        if (image.empty()) {
            throw new IllegalStateException("Unable to decode image " + imagePath);
        }
        return image;
    }

    private float[] predict(Mat image) {
        inputLayer.Reshape(1, numChannels, height, width);
        net.Reshape();
        MatVector inputChannels = wrapInputLayer();
        preprocess(image, inputChannels);
        net.Forward();

        FloatBlob outputLayer = net.output_blobs().get(0);
        FloatPointer data = outputLayer.cpu_data();
        int numOutputChannels = outputLayer.channels();
        float[] results = new float[numOutputChannels];
        for (int i = 0; i < numOutputChannels; i++) {
            results[i] = data.get(i);
        }
        return results;
    }

    private MatVector wrapInputLayer() {
        Mat[] inputChannels = new Mat[numChannels];
        FloatPointer inputData = net.input_blobs().get(0).mutable_cpu_data();
        for (int i = 0; i < numChannels; i++) {
            inputData.position(i * width * height * inputData.sizeof());
            inputChannels[i] = new Mat(height, width, CV_32FC1, inputData);
        }
        return new MatVector(inputChannels);
    }

    private void preprocess(Mat image, MatVector inputChannels) {
        // Convert the input image to the input image format of the network.
        Mat sample = new Mat();
        int imageChannels = image.channels();
        if (imageChannels == 3 && numChannels == 1) {
            cvtColor(image, sample, COLOR_BGR2GRAY);
        } else if (imageChannels == 4 && numChannels == 1) {
            cvtColor(image, sample, COLOR_BGRA2GRAY);
        } else if (imageChannels == 4 && numChannels == 3) {
            cvtColor(image, sample, COLOR_BGRA2BGR);
        } else if (imageChannels == 1 && numChannels == 3) {
            cvtColor(image, sample, COLOR_GRAY2BGR);
        } else {
            sample = image;
        }

        // resize image
        Mat sampleResized = new Mat();
        Size imageSize = sample.size();
        if (imageSize.width() != inputGeometry.width() || imageSize.height() != inputGeometry.height()) {
            resize(sample, sampleResized, inputGeometry);
        } else {
            sampleResized = sample;
        }

        // convert to float matrix
        Mat sampleFloat = new Mat();
        sampleResized.convertTo(sampleFloat, numChannels == 1 ? CV_32FC1 : CV_32FC3);

        // normalize image
        Mat sampleNormalized = new Mat();
        subtract(sampleFloat, mean, sampleNormalized);

        // This operation will write the separate BGR planes directly to the
        // input layer of the network because it is wrapped by the Mat
        // objects in inputChannels.
        split(sampleNormalized, inputChannels);
    }

    public static CaffeClassifier create(String modelFile, String trainedFile,
                                         String meanFile, String labelFile) throws IOException {
        // load trained model
        FloatNet net = new FloatNet(modelFile, TEST);
        net.CopyTrainedLayersFrom(trainedFile);

        // load mean image
        Mat mean = meanImage(meanFile, net);

        // load class labels
        List<String> labels;
        try (BufferedReader reader = new BufferedReader(new FileReader(labelFile))) {
            labels = reader.lines().collect(Collectors.toList());
        }

        return new CaffeClassifier(net, mean, labels);
    }

    private static Mat meanImage(String meanFile, FloatNet net) {
        BlobProto blobProto = new BlobProto();
        ReadProtoFromBinaryFileOrDie(meanFile, blobProto);
        FloatBlob meanBlob = new FloatBlob();
        meanBlob.FromProto(blobProto);
        int numChannels = meanBlob.channels();
        FloatBlob inputLayer = net.input_blobs().get(0);
        if (numChannels != inputLayer.channels()) {
            throw new IllegalStateException("Number of channels of mean file doesn't match input layer");
        }

        Mat[] channels = new Mat[numChannels];
        FloatPointer data = meanBlob.mutable_cpu_data();
        int meanHeight = meanBlob.height();
        int meanWidth = meanBlob.width();
        for (int i = 0; i < numChannels; i++) {
            data.position(i * meanHeight * meanWidth * data.sizeof());
            channels[i] = new Mat(meanHeight, meanWidth, CV_32FC1, data);
        }

        Mat mean = new Mat();
        merge(new MatVector(channels), mean);

        Scalar channelMean = mean(mean);
        return new Mat(inputLayer.height(), inputLayer.width(), mean.type(), channelMean);
    }

    public static class Prediction implements Comparable<Prediction> {

        public final String label;
        public final float confidence;

        public Prediction(String label, float confidence) {
            this.label = label;
            this.confidence = confidence;
        }

        @Override
        public int compareTo(Prediction o) {
            return Float.compare(o.confidence, confidence);
        }
    }
}
