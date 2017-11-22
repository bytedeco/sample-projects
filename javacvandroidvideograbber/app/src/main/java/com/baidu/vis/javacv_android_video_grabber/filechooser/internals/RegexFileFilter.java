package com.baidu.vis.javacv_android_video_grabber.filechooser.internals;/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
import java.io.File;
import java.io.FileFilter;
import java.util.regex.Pattern;

/**
 * Created by coco on 6/7/15.
 */
public class RegexFileFilter implements FileFilter {
    boolean mAllowHidden;
    boolean mOnlyDirectory;
    Pattern mPattern;

    public RegexFileFilter() {
        this(null);
    }

    public RegexFileFilter(Pattern ptn) {
        this(false, false, ptn);
    }

    public RegexFileFilter(boolean dirOnly, boolean hidden, String ptn) {
        mAllowHidden = hidden;
        mOnlyDirectory = dirOnly;
        mPattern = Pattern.compile(ptn, Pattern.CASE_INSENSITIVE);
    }

    public RegexFileFilter(boolean dirOnly, boolean hidden, String ptn, int flags) {
        mAllowHidden = hidden;
        mOnlyDirectory = dirOnly;
        mPattern = Pattern.compile(ptn, flags);
    }

    public RegexFileFilter(boolean dirOnly, boolean hidden, Pattern ptn) {
        mAllowHidden = hidden;
        mOnlyDirectory = dirOnly;
        mPattern = ptn;
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

        if (mPattern == null) {
            return true;
        }

        if (pathname.isDirectory()) {
            return true;
        }

        String name = pathname.getName();
        if (mPattern.matcher(name).matches()) {
            return true;
        }
        return false;
    }

}
