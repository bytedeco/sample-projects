/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package idl.baidu.page;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import idl.baidu.com.javacppgradle.R;

import static idl.baidu.cpp.MultiplyDemo.multiply;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(this, ">>"+multiply(123,100), Toast.LENGTH_SHORT).show();
    }
}
