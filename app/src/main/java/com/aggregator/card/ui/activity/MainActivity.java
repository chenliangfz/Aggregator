package com.aggregator.card.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.aggregator.card.R;
import com.aggregator.card.mock.Mocks;
import com.aggregator.card.core.inject.component.DaggerActivityComponent;
import com.aggregator.card.core.mvp.extension.StatusActivityView;
import com.aggregator.card.mock.SimpleRecyclerAdapter;
import com.aggregator.card.ui.activity.member.AdditionActivity;
import com.aggregator.card.ui.custom.recycle.HeaderAndFooterRecyclerView;
import com.aggregator.card.util.L;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;


public class MainActivity extends StatusActivityView<MainActivityPresenter> {

    private static final int REQUEST_QR_CODE = 0x1;

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
        }, 1000);
    }

    private View createHeaderView() {
        View view = LayoutInflater.from(this).inflate(android.R.layout.simple_list_item_activated_1, mStatusLayout, false);
        ((TextView) view.findViewById(android.R.id.text1)).setText(R.string.app_name);
        return view;
    }

    @OnClick(R.id.searcher)
    void onClickSearcher() {

    }


    @OnClick(R.id.addition)
    void onClickAddition() {
        startActivity(new Intent(this, AdditionActivity.class));
    }


    @OnClick(R.id.capture)
    void onClickCapture() {
        startActivityForResult(new Intent(this,SimpleCaptureActivity.class),REQUEST_QR_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK
                && requestCode == REQUEST_QR_CODE
                && data != null) {
            String result = data.getStringExtra("result");
            L.e("result:"+result);
            Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
        }
    }

}
