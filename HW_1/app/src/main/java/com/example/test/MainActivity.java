package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn = findViewById(R.id.button1);
        final TextView tv = findViewById(R.id.tv_title);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button btn_1 = findViewById(R.id.button1);
                EditText editText = (EditText) findViewById(R.id.editText);
                btn_1.setText("注册成功");
                tv.setText("Hello, "+ editText.getText().toString()+"!");
                Log.i("01", "用户注册");
            }
        });

        Log.i("02", "程序运行");
    }
}
