package com.baidu.vis.javacv_android_video_grabber.filechooser.internals;/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */

import java.io.File;
import java.io.FileFilter;

/**
 * Created by coco on 6/7/15.
 */
public class ExtFileFilter implements FileFilter {
    boolean mAllowHidden;
    boolean mOnlyDirectory;
    String[] mExt;

    public ExtFileFilter() {
        this(false, false);
    }

    public ExtFileFilter(String... extList) {
        this(false, false, extList);
    }

    public ExtFileFilter(boolean dirOnly, boolean hidden, String... extList) {
        mAllowHidden = hidden;
        mOnlyDirectory = dirOnly;
        mExt = extList;
    }

    @Override
    public boolean accept(File pathname) {
        if (!mAllowHidden) {
            if (pathname.isHidden()) {
                return false;
            }
        }

        if (mOnlyDirectory) {
            if (!pathname.isDirectory()) {
                return false;
            }
        }

        if (mExt == null) {
            return true;
        }

        if (pathname.isDirectory()) {
            return true;
        }

        String ext = FileUtil.getExtensionWithoutDot(pathname);
        for (String e : mExt) {
            if (ext.equalsIgnoreCase(e)) {
                return true;
            }
        }
        return false;
    }

}
