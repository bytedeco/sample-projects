package com.senter.lastools;

import org.bytedeco.javacpp.annotation.Platform;
import org.bytedeco.javacpp.annotation.Properties;
import org.bytedeco.javacpp.tools.Info;
import org.bytedeco.javacpp.tools.InfoMap;
import org.bytedeco.javacpp.tools.InfoMapper;

@Properties(
        // It's important that dependency headers go first, check https://github.com/bytedeco/javacpp/wiki/Mapping-Recipes#including-multiple-header-files
        value = @Platform(
                include = {
                        // LASzip/src folder
                        "mydefs.hpp", "lasquantizer.hpp", "lasattributer.hpp", "laszip.hpp", "laspoint.hpp"
                        // LASlib
                        , "lasvlr.hpp"
                        , "lasdefinitions.hpp", "lasutility.hpp", "lasignore.hpp", "laswaveform13writer.hpp", "laswaveform13reader.hpp"
                        , "laswriter.hpp", "lasreader.hpp"
                },
                // A <Library Name>.cpp will be generated
                library = "jniLASlib"),
        // A <Target Class Name>.java will be generated
        target = "com.senter.lastools.LasLib"
)
public class NativeLibraryConfig implements InfoMapper {
    static {
        // Let Android take care of loading JNI libraries for us
//        System.setProperty("org.bytedeco.javacpp.loadLibraries", "false");
    }

    public void map(InfoMap infoMap) {
        infoMap
                // skip the macro definition LASCopyString translation
                .put(new Info("LASCopyString").cppTypes("char *", "const char *").skip())
                // macro _MSC_VER should not be valid on Android
                .put(new Info("defined(_MSC_VER)").define(false))
                .put(new Info("_MSC_VER").define(false));
    }
}
