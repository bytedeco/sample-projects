/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package idl.baidu.page;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import idl.baidu.com.javacppgradle.R;

import static android.text.TextUtils.*;
import static idl.baidu.cpp.MultiplyDemo.multiply;

public class MainActivity extends Activity {

    private EditText firstParam;

    private EditText secondParam;
    private TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstParam = findViewById(R.id.first_param);
        secondParam = findViewById(R.id.second_param);
        result = findViewById(R.id.result);

        findViewById(R.id.multiply)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Editable firstParamText = firstParam.getText();
                        Editable secondParamText = secondParam.getText();

                        if (!isEmpty(firstParamText.toString()) && isDigitsOnly(firstParamText.toString()) && !isEmpty(secondParamText.toString()) && isDigitsOnly(secondParamText.toString())) {
                            int multiplyResult = multiply(Integer.parseInt(firstParamText.toString()), Integer.parseInt(secondParamText.toString()));
                            Toast.makeText(MainActivity.this, ">> " + multiplyResult, Toast.LENGTH_LONG).show();
                            result.setText(String.valueOf(multiplyResult));
                        }

                    }
                });

        Toast.makeText(MainActivity.this, ">>" + multiply(128, 256), Toast.LENGTH_LONG).show();
    }
}
