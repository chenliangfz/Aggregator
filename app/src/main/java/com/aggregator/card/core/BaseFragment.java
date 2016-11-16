package com.aggregator.card.core;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aggregator.card.core.inject.component.AppComponent;
import com.trello.rxlifecycle.components.support.RxFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by ChenLiang on 16/9/23.
 */

public abstract class BaseFragment extends RxFragment {

    private Unbinder mButterKnifeBinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutRes(), container, false);
        mButterKnifeBinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        mButterKnifeBinder.unbind();
        super.onDestroyView();
    }

    @NonNull
    protected abstract int getLayoutRes();

    private App getApplication() {
        return (App) getContext().getApplicationContext();
    }

    protected AppComponent getAppComponent() {
        return getApplication().getAppComponent();
    }
}
