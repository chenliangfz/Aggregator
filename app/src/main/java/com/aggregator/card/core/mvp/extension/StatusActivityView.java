package com.aggregator.card.core.mvp.extension;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.aggregator.card.R;
import com.aggregator.card.core.App;
import com.aggregator.card.core.inject.component.AppComponent;
import com.aggregator.card.core.mvp.view.BaseView;
import com.chenl.widgets.StatusLayout;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by ChenLiang on 16/11/8.
 */

public abstract class StatusActivityView<P extends StatusActivityPresenter> extends RxAppCompatActivity implements BaseView {

    @Inject
    protected P mPresenter;

    private Unbinder mButterKnifeBinder;

    @BindView(R.id.status_layout)
    protected StatusLayout mStatusLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        mButterKnifeBinder = ButterKnife.bind(this);
        initComponent();
        mPresenter.onAttach(this);
        onViewCreated(savedInstanceState);
    }

    @NonNull
    protected abstract int getLayoutResId();

    protected abstract void initComponent();

    protected abstract void onViewCreated(@Nullable Bundle savedInstanceState);

    @Override
    public void onDestroy() {
        mPresenter.onDestroy();
        mButterKnifeBinder.unbind();
        super.onDestroy();
    }

    protected AppComponent getAppComponent() {
        return ((App) getApplication()).getAppComponent();
    }
}
