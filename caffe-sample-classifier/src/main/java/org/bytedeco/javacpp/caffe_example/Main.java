package org.bytedeco.javacpp.caffe_example;

import java.io.IOException;
import java.util.List;

import static org.bytedeco.javacpp.caffe.Caffe;

public class Main {

    public static void main(String[] args) throws IOException {
        if (args.length != 5) {
            System.err.println("Usage: java -jar jarfile.jar " +
                    "deploy.prototxt network.caffemodel " +
                    "mean.binaryproto labels.txt image.jpg");
            System.exit(1);
        }

        String modelFile   = args[0];
        String trainedFile = args[1];
        String meanFile    = args[2];
        String labelFile   = args[3];

        Caffe.set_mode(Caffe.CPU);
        CaffeClassifier classifier = CaffeClassifier.create(modelFile, trainedFile, meanFile, labelFile);

        String imageFile   = args[4];
        System.out.format("---------- Prediction for %s ----------\n", imageFile);

        List<CaffeClassifier.Prediction> results = classifier.classify(imageFile, 5);
        for (CaffeClassifier.Prediction res : results) {
            System.out.println(res.label + ": " + res.confidence);
        }
    }
}
