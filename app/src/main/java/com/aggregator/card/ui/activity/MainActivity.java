package com.aggregator.card.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.aggregator.card.R;
import com.aggregator.card.mock.Mocks;
import com.aggregator.card.core.inject.component.DaggerActivityComponent;
import com.aggregator.card.core.mvp.extension.StatusActivityView;
import com.aggregator.card.mock.SimpleRecyclerAdapter;
import com.aggregator.card.ui.custom.recycle.HeaderAndFooterRecyclerView;

import javax.inject.Inject;

import butterknife.BindView;


public class MainActivity extends StatusActivityView<MainActivityPresenter> {

    @BindView(R.id.header_footer_recycler)
    HeaderAndFooterRecyclerView mRecyclerView;

    @Inject
    SimpleRecyclerAdapter mAdapter;

    @NonNull
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initComponent() {
        DaggerActivityComponent.builder().appComponent(getAppComponent()).build().inject(this);
    }

    @Override
    protected void onViewCreated(@Nullable Bundle savedInstanceState) {
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addHeaderView(createHeaderView());
        mRecyclerView.addHeaderView(createHeaderView());
        mRecyclerView.addHeaderView(createHeaderView());
        mRecyclerView.addFooterView(createHeaderView());
        mRecyclerView.addFooterView(createHeaderView());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mAdapter.addAll(Mocks.mMockValuse);
                mStatusLayout.hideLoadingLayout();
            }
        },1000);
    }

    private View createHeaderView() {
        View view = LayoutInflater.from(this).inflate(android.R.layout.simple_list_item_activated_1,mStatusLayout,false);
        ((TextView)view.findViewById(android.R.id.text1)).setText(R.string.app_name);
        return view;
    }

}
