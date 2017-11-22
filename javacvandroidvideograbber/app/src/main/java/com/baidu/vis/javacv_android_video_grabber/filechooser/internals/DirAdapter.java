package com.baidu.vis.javacv_android_video_grabber.filechooser.internals;/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.baidu.vis.javacv_android_video_grabber.R;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * Created by coco on 6/7/15.
 */
public class DirAdapter extends ArrayAdapter<File> {

    static SimpleDateFormat _formatter;

    List<File> mEntries;

    public DirAdapter(Context cxt, List<File> entries, int resId) {
        super(cxt, resId, R.id.text1, entries);
        this.init(entries, null);
    }

    public DirAdapter(Context cxt, List<File> entries, int resId, String dateFormat) {
        super(cxt, resId, R.id.text1, entries);
        this.init(entries, dateFormat);
    }

    public DirAdapter(Context cxt, List<File> entries, int resource, int textViewResourceId) {
        super(cxt, resource, textViewResourceId, entries);
        this.init(entries, null);
    }

    private void init(List<File> entries, String dateFormat) {
        _formatter = new SimpleDateFormat(dateFormat != null && !"".equals(dateFormat.trim())
                ? dateFormat.trim() : "yyyy/MM/dd HH:mm:ss");
        mEntries = entries;
    }

    // This function is called to show each view item
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewGroup rl = (ViewGroup) super.getView(position, convertView, parent);
        if (rl == null) {
            return null;
        }

        TextView tvName = (TextView) rl.findViewById(R.id.text1);
        TextView tvSize = (TextView) rl.findViewById(R.id.txt_size);
        TextView tvDate = (TextView) rl.findViewById(R.id.txt_date);
        tvDate.setVisibility(View.VISIBLE);

        File file = mEntries.get(position);
        if (file == null) {
            tvName.setText("..");
//            tvName.setCompoundDrawablesWithIntrinsicBounds( ContextCompat.getDrawable( getContext(), R.drawable.ic_folder ), null, null, null );
            tvDate.setVisibility(View.GONE);
        } else if (file.isDirectory()) {
            tvName.setText(mEntries.get(position).getName());
//            tvName.setCompoundDrawablesWithIntrinsicBounds( ContextCompat.getDrawable( getContext(), R.drawable.ic_folder ), null, null, null );
            tvSize.setText("");
            if (!mEntries.get(position).getName().trim().equals("..")) {
                tvDate.setText(_formatter.format(new Date(file.lastModified())));
            } else {
                tvDate.setVisibility(View.GONE);
            }
        } else {
            tvName.setText(mEntries.get(position).getName());
//            tvName.setCompoundDrawablesWithIntrinsicBounds( ContextCompat.getDrawable( getContext(), R.drawable.ic_file ), null, null, null );
            tvSize.setText(FileUtil.getReadableFileSize(file.length()));
            tvDate.setText(_formatter.format(new Date(file.lastModified())));
        }

        return rl;
    }
}

