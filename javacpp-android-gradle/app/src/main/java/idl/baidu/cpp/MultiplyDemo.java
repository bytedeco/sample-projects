/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package idl.baidu.cpp;

import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.annotation.Namespace;
import org.bytedeco.javacpp.annotation.Platform;
import org.bytedeco.javacpp.annotation.StdString;

@Platform(include="NativeLibrary.h",link="MultiplyDemo")
public class MultiplyDemo {

    static {
        Loader.load();
    }

    public static native int multiply(int a, int b);
}