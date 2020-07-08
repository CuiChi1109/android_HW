package com.example.chapter3.homework;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;

/*import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;*/

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import com.example.chapter3.homework.LinearItemDecoration;
import com.example.chapter3.homework.MyAdapter;
import com.example.chapter3.homework.TestData;
import com.example.chapter3.homework.TestDataSet;

import android.animation.AnimatorSet;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class FriendListFragment extends Fragment {
    private static final String TAG = "FriendListFragment";
    private AnimatorSet animatorSet;
    private View target;
    private int contentView;
    private RecyclerView recyclerView;
    private MyAdapter mAdapter;
    //private RecyclerView.LayoutManager layoutManager;
    LinearLayoutManager linear;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView() called with: inflater = [" + inflater + "], container = [" + container + "], savedInstanceState = [" + savedInstanceState + "]");

        return inflater.inflate(R.layout.fragment_friendlist, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @NonNull Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        target = view.findViewById(R.id.animation_view_1);
        fade_in_and_out();
        initView();
    }

    private void initView() {
        recyclerView = recyclerView.findViewById(R.id.recycler);


        //layoutManager = new LinearLayoutManager(this);
        //GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);


        mAdapter = new MyAdapter(TestDataSet.getData());
        linear = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        //mAdapter.setOnItemClickListener((MyAdapter.IOnItemClickListener) this);
        //recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(linear);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mAdapter);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach() called with: context = [" + context + "]");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_ch3ex2);
        //target = target.findViewById(R.id.animation_view_1);
        //fade_in();
        Log.d(TAG, "onCreate() called with: savedInstanceState = [" + savedInstanceState + "]");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated() called with: savedInstanceState = [" + savedInstanceState + "]");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView() called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach() called");
    }

    private void fade_in_and_out() {
        if (animatorSet != null) {
            animatorSet.cancel();
        }
        //实现淡入淡出
        ObjectAnimator animator_fade_in = ObjectAnimator.ofFloat(target, "alpha", 0, 1.0f);
        animator_fade_in.setRepeatCount(1);
        animator_fade_in.setInterpolator(new LinearInterpolator());
        animator_fade_in.setDuration(2000);
        animator_fade_in.setRepeatMode(ValueAnimator.REVERSE);

        animatorSet = new AnimatorSet();
        animatorSet.playTogether(animator_fade_in);
        animatorSet.start();
    }


    public void setContentView(int contentView) {
        this.contentView = contentView;
    }

    public int getContentView() {
        return contentView;
    }
}
