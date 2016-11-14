package com.aggregator.card.ui.activity.member;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.EditText;

import com.aggregator.card.R;
import com.aggregator.card.core.inject.component.DaggerActivityComponent;
import com.aggregator.card.core.mvp.extension.RxAppCompatActivityView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ChenLiang on 16/11/14.
 */

public class AdditionActivity extends RxAppCompatActivityView<AdditionPresenter> {

    @BindView(R.id.business)
    EditText mEdtBusiness;

    @NonNull
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_addition;
    }

    @Override
    protected void initComponent() {
        DaggerActivityComponent.builder().appComponent(getAppComponent()).build().inject(this);
    }

    @Override
    protected void onViewCreated(@Nullable Bundle savedInstanceState) {

    }

    @OnClick(R.id.screenshot_captor)
    void onClickScreenshotCaptor(){

    }

    @OnClick(R.id.sure)
    void onClickSure(){

    }

    @OnClick(R.id.back)
    void onClickBack(){
        finish();
    }
}
