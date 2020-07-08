package com.example.chapter3.homework;


import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;

import com.airbnb.lottie.LottieAnimationView;
import com.example.chapter3.homework.MyAdapter;
import com.example.chapter3.homework.TestDataSet;

public class PlaceholderFragment extends Fragment {

    private static final String TAG = "PlaceholderFragment";
    private AnimatorSet animatorSet;
    private View target;
    private int contentView;
    private RecyclerView recyclerView;
    private MyAdapter mAdapter;
    LinearLayoutManager linear;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO ex3-3: 修改 fragment_placeholder，添加 loading 控件和列表视图控件
        return inflater.inflate(R.layout.fragment_placeholder, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        target = view.findViewById(R.id.animation_view_2);
        recyclerView = view.findViewById(R.id.recycler_1);

        mAdapter = new MyAdapter(TestDataSet.getData());
        linear = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(linear);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mAdapter);

        fade_in();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getView().postDelayed(new Runnable() {
            @Override
            public void run() {
                // 这里会在 5s 后执行
                // TODO ex3-4：实现动画，将 lottie 控件淡出，列表数据淡入
                ObjectAnimator animatorList = ObjectAnimator.ofFloat(recyclerView,"alpha",0f,1f);
                animatorList.setDuration(500);
                recyclerView.setAlpha(0f);
                recyclerView.setVisibility(View.VISIBLE);
                animatorList.start();

                getView().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        target.setVisibility(View.INVISIBLE);
                    }
                },500);

            }
        }, 5000);
    }

    public void fade_in(){
        if (animatorSet != null) {
            animatorSet.cancel();
        }
        //实现淡入淡出
        ObjectAnimator animator_fade_in = ObjectAnimator.ofFloat(target, "alpha", 0, 1.0f);
        animator_fade_in.setRepeatCount(1);
        animator_fade_in.setInterpolator(new LinearInterpolator());
        animator_fade_in.setDuration(2200);
        animator_fade_in.setRepeatMode(ValueAnimator.REVERSE);

        animatorSet = new AnimatorSet();
        animatorSet.playTogether(animator_fade_in);
        animatorSet.start();
    }
}
