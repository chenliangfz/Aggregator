package com.aggregator.card.core;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.aggregator.card.core.inject.component.AppComponent;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by ChenLiang on 16/11/16.
 */

public abstract class BaseActivity extends AppCompatActivity {

    private Unbinder mButterKnifeBinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        mButterKnifeBinder = ButterKnife.bind(this);
        initComponent();
        onViewCreated(savedInstanceState);
    }

    @NonNull
    protected abstract int getLayoutResId();

    protected abstract void initComponent();

    protected abstract void onViewCreated(@Nullable Bundle savedInstanceState);

    @Override
    public void onDestroy() {
        mButterKnifeBinder.unbind();
        super.onDestroy();
    }

    protected AppComponent getAppComponent() {
        return ((App) getApplication()).getAppComponent();
    }
}
