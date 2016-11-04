package com.aggregator.card.presenter;


import com.aggregator.card.ui.view.BaseView;

/**
 * Created by ChenLiang on 16/9/26.
 */

public class BasePresenter<T extends BaseView> {
    private T mBaseView;

    public void attachView(T mBaseView) {
        this.mBaseView = mBaseView;
    }

    public void detachView() {
        this.mBaseView = null;
    }
}
