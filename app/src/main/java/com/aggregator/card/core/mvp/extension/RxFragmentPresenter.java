package com.aggregator.card.core.mvp.extension;

import com.aggregator.card.core.mvp.presenter.BasePresenter;
import com.aggregator.card.core.mvp.view.BaseView;

/**
 * Created by ChenLiang on 16/11/4.
 */

public class RxFragmentPresenter<V extends BaseView> implements BasePresenter<V> {
    protected V mView;

    @Override
    public void onAttach(V view) {
        this.mView = view;
    }

    public void onDestroy() {
        this.mView = null;
    }
}
