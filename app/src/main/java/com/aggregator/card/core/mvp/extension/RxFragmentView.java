package com.aggregator.card.core.mvp.extension;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aggregator.card.core.App;
import com.aggregator.card.core.BaseFragment;
import com.aggregator.card.core.inject.component.AppComponent;
import com.aggregator.card.core.inject.scope.FragmentScope;
import com.trello.rxlifecycle.components.support.RxFragment;
import com.aggregator.card.core.mvp.view.BaseView;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by ChenLiang on 16/11/4.
 */
@FragmentScope
public abstract class RxFragmentView<P extends RxFragmentPresenter> extends BaseFragment implements BaseView {
    @Inject
    P mPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        initComponent();
        mPresenter.onAttach(this);
        return view;
    }

    @Override
    public void onDestroyView() {
        mPresenter.onDestroy();
        super.onDestroyView();
    }

    protected abstract void initComponent();
}
