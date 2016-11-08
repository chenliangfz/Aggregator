package com.aggregator.card.core.mvp.extension;

import com.aggregator.card.core.mvp.presenter.BasePresenter;

/**
 * Created by ChenLiang on 16/11/8.
 */

public class StatusActivityPresenter<V extends StatusActivityView> implements BasePresenter<V> {

    protected V mView;

    @Override
    public void onAttach(V view) {
        this.mView = view;
    }

    public void onDestroy() {
        this.mView = null;
    }
}
