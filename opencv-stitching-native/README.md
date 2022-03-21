Sample project for using OpenCV from a generated Java binding made with [Bytedeco's](http://bytedeco.org/) JavaCPP technology.
 * A native executable image version of the [original stitching sample project](../opencv-stitching) using [GraalVM](https://www.graalvm.org/) and its AOT compilation.

For this to work, please first download and install GraalVM 21.2.0, making sure that the `native-image` tool also gets installed:
 * https://www.graalvm.org/downloads/
 * https://www.graalvm.org/dev/docs/getting-started/#native-images

Then we can build this project using Maven as usual, but it must be executed with GraalVM as the JDK, for example, on the command line, run:

```bash
$ export JAVA_HOME=/path/to/graalvm/
$ mvn clean package -Djavacpp.platform.custom -Djavacpp.platform.host
$ target/stitching panorama_image1.jpg panorama_image2.jpg --output panorama_stitched.jpg --try_use_gpu yes
```

First time through, this is going to go off to the Maven Central Repository and download artifacts, including the native libraries for Linux, Mac OS X, or Windows (according to your current platform). 

After running the above, take a look at the resulting file `panorama_stitched.jpg`. The two pictures to stitch are taken from [Ramsri Goutham's blog entry on stitching panoramas with OpenCV](https://ramsrigoutham.wordpress.com/2012/11/22/panorama-image-stitching-in-opencv/).

The way that Java interoperates with OpenCV is fairly faithful to the original API, including capitalization. The classes and methods in the resulting Java binding have the same case as the C++ API. Indeed many are static methods.

There's command line passing going on, which strictly speaking is not really needed for a sample, as file names could be hard coded. It allows you to experiment a little, though.

After running the sample, you should be comfortable enough to use OpenCV in a Java solution of your own. Be aware, though, that [JavaCV](https://github.com/bytedeco/javacv) is a more developed framework around OpenCV and other libraries for real-world usage.

----
Authors: [Paul Hammant](https://github.com/paul-hammant/) and [Samuel Audet](https://github.com/saudet)
