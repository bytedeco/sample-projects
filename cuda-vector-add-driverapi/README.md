Sample file showing how to use the [CUDA](https://developer.nvidia.com/cuda-zone) [Driver API](http://docs.nvidia.com/cuda/cuda-driver-api/) (another [link](http://docs.nvidia.com/cuda/cuda-c-programming-guide/index.html#driver-api)) to run kernels from Java with the [javacpp cuda-platform](https://github.com/bytedeco/javacpp-presets/tree/master/cuda).

Use your favorite IDE or environment to run the sample or just have a look. You need to have a CUDA-capable device and the CUDA drivers and toolkit installed.
I used NetBeans; follow theses steps if you want to use Netbeans:
- create a new project (type `Maven`, `Java Application`)
- add the dependency `org.bytedeco.javacpp-presets`, `cuda-platform`, `9.1-7.1-1.4.1` (or newer version); 6 additional dependencies will be included automagically
- add the sample, build and run

The sample is a combination of the following sources:
- the CUDA example [`0_Simple/vectorAddDrv/`](http://docs.nvidia.com/cuda/cuda-samples/index.html#vector-addition-driver-api), which hints to chapter 3 of the programming guide
- https://developer.download.nvidia.com/books/cuda-by-example/cuda-by-example-sample.pdf (section 4.2.1)
- also you may want to have a look at https://developer.nvidia.com/how-to-cuda-c-cpp

This sample should be reasonable similar to the respective JCuda [sample](http://www.jcuda.org/tutorial/TutorialIndex.html).

If you are already familiar with CUDA programming the most interesting aspect about this sample might be the handling of pointers (to device memory, to JVM native memory and to result parameters of CUDA procedures) in `javacpp`. Please note that you might use `<type>[]`, `<Type>Pointer` and `<Type>Buffer` somewhat interchangeably in most cases (to the knowledge of the author, but try it yourself ;) ).

In the code I tried to make clear where the methods come from; in production code you may want to use static imports. To have the code more readable, it is very simplified, has no error checking, etc. There's a deliberate error introduced into one of the input vectors to show that the check really checks and that all other values are as expected.

---
Author: https://github.com/ka-ba/
