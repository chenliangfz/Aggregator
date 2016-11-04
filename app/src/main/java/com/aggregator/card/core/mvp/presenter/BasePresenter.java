package com.aggregator.card.core.mvp.presenter;


import com.aggregator.card.core.mvp.view.BaseView;

/**
 * Created by ChenLiang on 16/11/4.
 */

public interface BasePresenter<V extends BaseView> {
    public void onAttach(V view);
}

