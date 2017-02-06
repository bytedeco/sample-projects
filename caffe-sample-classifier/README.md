# Caffe sample classifier
Example of using [JavaCPP Presets for Caffe](https://github.com/bytedeco/javacpp-presets/tree/master/caffe) for image classification.
It is java version of [original C++ caffe example](https://github.com/BVLC/caffe/tree/master/examples/cpp_classification).

## Requirements
* Linux or MacOS
* java 8
* CUDA 8

## Build
`./gradlew shadowJar`

## Run
`java -jar jarfile.jar deploy.prototxt network.caffemodel mean.binaryproto labels.txt image.jpg`


