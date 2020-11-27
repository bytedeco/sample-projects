package com.example.myapplication;

import org.bytedeco.javacpp.*;
import org.bytedeco.javacpp.annotation.*;
import org.bytedeco.javacpp.tools.*;

@Properties(
    value = @Platform(include = "NativeLibrary.h"),
    target = "com.example.myapplication.NativeLibrary"
)
public class NativeLibraryConfig implements InfoMapper {
    static {
        // Let Android take care of loading JNI libraries for us
        System.setProperty("org.bytedeco.javacpp.loadLibraries", "false");
    }

    public void map(InfoMap infoMap) {
    }
}
