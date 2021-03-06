package com.aggregator.card.core.mvp.extension;

import com.aggregator.card.core.inject.scope.ActivityScope;
import com.aggregator.card.core.mvp.presenter.BasePresenter;

import javax.inject.Inject;

/**
 * Created by ChenLiang on 16/11/4.
 */
@ActivityScope
public abstract class RxAppcompatActivityPresenter<V extends RxAppCompatActivityView> implements BasePresenter<V> {

    protected V mView;

    @Override
    public void onAttach(V view) {
        this.mView = view;
    }

    public void onDestroy() {
        this.mView = null;
    }
}
