package com.example.hw_2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hw_2.recycler.LinearItemDecoration;
import com.example.hw_2.recycler.MyAdapter;
import com.example.hw_2.recycler.TestData;
import com.example.hw_2.recycler.TestDataSet;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import static com.example.hw_2.R.layout.activity_main;

public class MainActivity extends AppCompatActivity implements MyAdapter.IOnItemClickListener{

 /*   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }*/

    private static final String TAG = "TAG";

    private RecyclerView recyclerView;
    private MyAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private GridLayoutManager gridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_main);
        Log.i(TAG, "RecyclerViewActivity onCreate");
        initView();
    }

    @SuppressLint("ResourceType")
    private void initView() {
        recyclerView = findViewById(R.id.recycler);

        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new MyAdapter(TestDataSet.getData());
        mAdapter.setOnItemClickListener((MyAdapter.IOnItemClickListener) this);
        recyclerView.setAdapter(mAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        printCount(findViewById(R.id.linearLayout));
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "RecyclerViewActivity onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "RecyclerViewActivity onResume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "MainActivity onRestart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "MainActivity onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "MainActivity onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "MainActivity onDestroy");
    }

    public void onItemCLick(int position, TestData data) {
        Toast.makeText(MainActivity.this, "点击了第" + position + "条", Toast.LENGTH_SHORT).show();
        //mAdapter.addData(position + 1, new TestData("新增头条", "0w"));
        Intent intent = new Intent(this,NumberActivity.class);
        intent.putExtra("n", "这是第"+position+"条消息");
        startActivity(intent);
        Log.i(TAG, "Number");
    }

    @Override
    public void onItemLongCLick(int position, TestData data) {

    }

    public int viewCount(View root) {
        int Count = 0;

        if (null == root) {
            return 0;
        }

        if (root instanceof ViewGroup) {
            Count++;
            for (int i = 0; i < ((ViewGroup) root).getChildCount(); i++) {
                View view = ((ViewGroup) root).getChildAt(i);
                if (view instanceof ViewGroup) {
                    Count += viewCount(view);
                } else {
                    Count++;
                }
            }
        }

        return Count;
    }


    public void printCount(View root){
        int Count = viewCount(root);
        Toast.makeText(MainActivity.this, "View number is " + String.valueOf(Count), Toast.LENGTH_SHORT).show();
        Log.i(TAG, "View number is "+String.valueOf(Count));
    }
}