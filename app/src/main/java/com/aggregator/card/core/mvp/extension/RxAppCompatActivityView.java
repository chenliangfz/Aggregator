package com.aggregator.card.core.mvp.extension;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.aggregator.card.core.App;
import com.aggregator.card.core.inject.component.AppComponent;
import com.aggregator.card.core.mvp.view.BaseView;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by ChenLiang on 16/11/4.
 */

public abstract class RxAppCompatActivityView<P extends RxAppcompatActivityPresenter> extends RxAppCompatActivity implements BaseView {

    @Inject
    protected P presenter;

    private Unbinder mButterKnifeBinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        mButterKnifeBinder = ButterKnife.bind(this);
        initComponent();
        presenter.onAttach(this);
        onViewCreated(savedInstanceState);
    }

    @NonNull
    protected abstract int getLayoutResId();

    protected abstract void initComponent();

    protected abstract void onViewCreated(@Nullable Bundle savedInstanceState);

    @Override
    public void onDestroy() {
        presenter.onDestroy();
        mButterKnifeBinder.unbind();
        super.onDestroy();
    }

    protected AppComponent getAppComponent() {
        return ((App) getApplication()).getAppComponent();
    }
}
