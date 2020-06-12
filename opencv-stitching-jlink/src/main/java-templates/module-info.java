module org.bytedeco.samples.stitching {

    /* Option 1: bring only the java artifacts into the module graph.
     * This option assumes the needed native libraries are installed
     * in the the jlink archive with some other way.
     */
    //requires org.bytedeco.opencv;

    /* Option 2: bring the native libraries corresponding the the
     * configured javacpp.platform */
    requires org.bytedeco.opencv.${javacpp.platform.module};

    /* Option 3: bring all native libraries of all platforms.
     * The invocation of the build-helper-maven-plugin must be removed from the pom.  */
    //requires org.bytedeco.opencv.platform;
}
