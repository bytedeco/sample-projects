package com.baidu.vis.javacv_android_video_grabber.filechooser.internals;/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */

import java.io.File;
import java.text.DecimalFormat;

/**
 * Created by coco on 6/7/15.
 */
public class FileUtil {


    public static String getExtension(File file) {
        if (file == null) {
            return null;
        }

        int dot = file.getName().lastIndexOf(".");
        if (dot >= 0) {
            return file.getName().substring(dot);
        } else {
            // No extension.
            return "";
        }
    }

    public static String getExtensionWithoutDot(File file) {
        String ext = getExtension(file);
        if (ext.length() == 0) {
            return ext;
        }
        return ext.substring(1);
    }

    public static String getReadableFileSize(long size) {
        final int bytesInKilobytes = 1024;
        final DecimalFormat dec = new DecimalFormat("###.#");
        final String kilobytes = " KB";
        final String megabytes = " MB";
        final String gigabytes = " GB";
        float fileSize = 0;
        String suffix = kilobytes;

        if (size > bytesInKilobytes) {
            fileSize = size / bytesInKilobytes;
            if (fileSize > bytesInKilobytes) {
                fileSize = fileSize / bytesInKilobytes;
                if (fileSize > bytesInKilobytes) {
                    fileSize = fileSize / bytesInKilobytes;
                    suffix = gigabytes;
                } else {
                    suffix = megabytes;
                }
            }
        }
        return String.valueOf(dec.format(fileSize) + suffix);
    }

}
