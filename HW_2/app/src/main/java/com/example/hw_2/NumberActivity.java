package com.example.hw_2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class NumberActivity extends AppCompatActivity {

    private static final String TAG = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.number);
        TextView tv_1=findViewById(R.id.tv_number_1);
        Intent intent =getIntent();
        String first = intent.getStringExtra("n");
        tv_1.setText(first);
        Log.i(TAG, "Number onCreate");
        initView();
    }

    private void initView() {
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "Number onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "Number onResume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "Number onRestart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "Number onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "Number onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "Number onDestroy");
    }

}
