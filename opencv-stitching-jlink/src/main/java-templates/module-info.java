module ${outputModule} {

    /* Option 1: bring only the java artifacts into the module graph.
     * This option assumes the needed native libraries are installed
     * in the the jlink archive using some other way.
     */
    //requires org.bytedeco.opencv;

    /* Option 2: bring the native libraries corresponding the the
     * configured javacpp.platform */
    requires org.bytedeco.opencv.${javacpp.platform.module};

    /* Option 3: bring all native libraries of all platforms. */
    //requires org.bytedeco.opencv.platform;
}
