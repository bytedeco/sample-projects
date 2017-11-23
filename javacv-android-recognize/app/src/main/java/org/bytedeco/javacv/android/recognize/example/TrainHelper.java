/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bytedeco.javacv.android.recognize.example;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.IntBuffer;
import static org.bytedeco.javacpp.opencv_core.CV_32SC1;

import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.MatVector;
import org.bytedeco.javacpp.opencv_core.Size;
import org.bytedeco.javacpp.opencv_face.FaceRecognizer;
import org.bytedeco.javacpp.opencv_objdetect;

import static org.bytedeco.javacpp.opencv_imgcodecs.imwrite;
import static org.bytedeco.javacpp.opencv_imgproc.rectangle;

import static org.bytedeco.javacpp.opencv_face.createEigenFaceRecognizer;
import static org.bytedeco.javacpp.opencv_face.createFisherFaceRecognizer;
import static org.bytedeco.javacpp.opencv_face.createLBPHFaceRecognizer;
import static org.bytedeco.javacpp.opencv_imgcodecs.CV_LOAD_IMAGE_GRAYSCALE;
import static org.bytedeco.javacpp.opencv_imgcodecs.imread;
import static org.bytedeco.javacpp.opencv_imgproc.CV_BGR2GRAY;
import static org.bytedeco.javacpp.opencv_imgproc.cvtColor;
import static org.bytedeco.javacpp.opencv_imgproc.resize;

/**
 *
 * @author djalmaafilho
 */
public class TrainHelper {

    public static final String TAG = "TrainHelper";
    public static final String TRAIN_FOLDER = "train_folder";
    public static final int IMG_SIZE = 160;

    public static final String EIGEN_FACES_CLASSIFIER = "eigenFacesClassifier.yml";
    public static final String FISHER_FACES_CLASSIFIER = "fisherFacesClassifier.yml";
    public static final String LBPH_CLASSIFIER = "lbphClassifier.yml";
    public static final String FILE_NAME_PATTERN = "person.%d.%d.jpg";
    public static final int PHOTOS_TRAIN_QTY = 25;
    public static final double ACCEPT_LEVEL = 4000.0D;


    public static void reset(Context context) throws Exception {
        File photosFolder = new File(context.getFilesDir(), TRAIN_FOLDER);
        if(photosFolder.exists()) {

            FilenameFilter imageFilter = new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return  name.endsWith(".jpg") || name.endsWith(".gif") || name.endsWith(".png")  ||name.endsWith(".yml");
                }
            };

            File[] files = photosFolder.listFiles(imageFilter);
            for(File file : files) {
                file.delete();
            }
        }
    }


    public static boolean isTrained(Context context) {
        try {
            File photosFolder = new File(context.getFilesDir(), TRAIN_FOLDER);
            if(photosFolder.exists()) {

                FilenameFilter imageFilter = new FilenameFilter() {
                    @Override
                    public boolean accept(File dir, String name) {
                        return  name.endsWith(".jpg") || name.endsWith(".gif") || name.endsWith(".png");
                    }
                };

                FilenameFilter trainFilter = new FilenameFilter() {
                    @Override
                    public boolean accept(File dir, String name) {
                        return  name.endsWith(".yml");
                    }
                };

                File[] photos = photosFolder.listFiles(imageFilter);
                File[] train = photosFolder.listFiles(trainFilter);
                return photos!= null && train!= null && photos.length == PHOTOS_TRAIN_QTY && train.length > 0;
            } else {
                return false;
            }

        }catch (Exception e) {
            Log.d(TAG, e.getLocalizedMessage(), e);
        }
        return false;
    }

    public static int qtdPhotos(Context context) {
        File photosFolder = new File(context.getFilesDir(), TRAIN_FOLDER);
        if(photosFolder.exists()) {
            FilenameFilter imageFilter = new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return  name.endsWith(".jpg") || name.endsWith(".gif") || name.endsWith(".png");
                }
            };

            File[] files = photosFolder.listFiles(imageFilter);
            return files != null ? files.length : 0;
        }
        return 0;
    }

    public static boolean train(Context context) throws Exception{

        File photosFolder = new File(context.getFilesDir(), TRAIN_FOLDER);
        if(!photosFolder.exists()) return false;

        FilenameFilter imageFilter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return  name.endsWith(".jpg") || name.endsWith(".gif") || name.endsWith(".png");
            }
        };
        
        File[] files = photosFolder.listFiles(imageFilter);
        MatVector photos = new MatVector(files.length);
        Mat labels = new Mat(files.length, 1, CV_32SC1);
        IntBuffer rotulosBuffer = labels.createBuffer();
        int counter = 0;
        for (File image: files) {
            Mat photo = imread(image.getAbsolutePath(), CV_LOAD_IMAGE_GRAYSCALE);
            int classe = Integer.parseInt(image.getName().split("\\.")[1]);
            resize(photo, photo, new Size(IMG_SIZE,IMG_SIZE));
            photos.put(counter, photo);
            rotulosBuffer.put(counter, classe);
            counter++;
        }
        
        FaceRecognizer eigenfaces = createEigenFaceRecognizer();
        FaceRecognizer fisherfaces = createFisherFaceRecognizer();
        FaceRecognizer lbph = createLBPHFaceRecognizer(2,9,9,9,1);

        eigenfaces.train(photos, labels);
        File f = new File(photosFolder, EIGEN_FACES_CLASSIFIER);
        f.createNewFile();
        eigenfaces.save(f.getAbsolutePath());

//TODO: Implement this other classifiers
//        fisherfaces.train(photos, labels);
//        f = new File(photosFolder, FISHER_FACES_CLASSIFIER);
//        f.createNewFile();
//        fisherfaces.save(f.getAbsolutePath());
//
//        lbph.train(photos, labels);
//        f = new File(photosFolder, LBPH_CLASSIFIER);
//        f.createNewFile();
//        lbph.save(f.getAbsolutePath());
        return true;
    }

    public static void takePhoto(Context context, int personId, int photoNumber, Mat rgbaMat, opencv_objdetect.CascadeClassifier faceDetector) throws Exception{
        File folder = new File(context.getFilesDir(), TRAIN_FOLDER);
        if(folder.exists() && !folder.isDirectory())
            folder.delete();
        if(!folder.exists())
            folder.mkdirs();

        int qtyPhotos = PHOTOS_TRAIN_QTY;
        Mat greyMat = new Mat(rgbaMat.rows(), rgbaMat.cols());

        cvtColor(rgbaMat, greyMat, CV_BGR2GRAY);
        opencv_core.RectVector detectedFaces = new opencv_core.RectVector();
        faceDetector.detectMultiScale(greyMat, detectedFaces, 1.1, 1, 0, new Size(150,150), new Size(500,500));
        for (int i = 0; i < detectedFaces.size(); i++) {

            opencv_core.Rect rectFace = detectedFaces.get(0);
            rectangle(rgbaMat, rectFace, new opencv_core.Scalar(0,0,255, 0));
            Mat capturedFace = new Mat(greyMat, rectFace);
            resize(capturedFace, capturedFace, new Size(IMG_SIZE,IMG_SIZE));

            if (photoNumber <= qtyPhotos) {
                File f = new File(folder, String.format(FILE_NAME_PATTERN, personId, photoNumber));
                f.createNewFile();
                imwrite(f.getAbsolutePath(), capturedFace);
            }
        }
    }


    public static opencv_objdetect.CascadeClassifier loadClassifierCascade(Context context, int resId) {
        FileOutputStream fos = null;
        InputStream inputStream;

        inputStream = context.getResources().openRawResource(resId);
        File xmlDir = context.getDir("xml", Context.MODE_PRIVATE);
        File cascadeFile = new File(xmlDir, "temp.xml");
        try {
            fos = new FileOutputStream(cascadeFile);
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            Log.d(TAG, "Can\'t load the cascade file");
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        opencv_objdetect.CascadeClassifier detector = new opencv_objdetect.CascadeClassifier(cascadeFile.getAbsolutePath());
        if (detector.isNull()) {
            Log.e(TAG, "Failed to load cascade classifier");
            detector = null;
        } else {
            Log.i(TAG, "Loaded cascade classifier from " + cascadeFile.getAbsolutePath());
        }
        // delete the temporary directory
        cascadeFile.delete();

        return detector;
    }
}
