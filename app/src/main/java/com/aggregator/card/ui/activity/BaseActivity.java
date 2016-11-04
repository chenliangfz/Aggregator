package com.aggregator.card.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;



import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by ChenLiang on 16/9/23.
 */

public abstract class BaseActivity extends AppCompatActivity {

    private Unbinder mButterKnifeBinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        initTheme();
        mButterKnifeBinder = ButterKnife.bind(this);
        initPresenter();
        initView();
    }

    private void initTheme() {
    }

    protected abstract void initPresenter();


    protected abstract void initView();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mButterKnifeBinder.unbind();
    }

    @NonNull
    protected abstract int getLayoutResId();


}



