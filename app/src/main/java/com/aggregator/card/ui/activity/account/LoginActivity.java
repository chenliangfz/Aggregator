package com.aggregator.card.ui.activity.account;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;

import com.aggregator.card.R;
import com.aggregator.card.core.inject.component.DaggerActivityComponent;
import com.aggregator.card.core.mvp.extension.RxAppCompatActivityView;
import com.aggregator.card.ui.activity.MainActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ChenLiang on 16/11/13.
 */

public class LoginActivity extends RxAppCompatActivityView<LoginPresenter> {


    @BindView(R.id.account)
    EditText mEdtAccount;

    @BindView(R.id.password)
    EditText mEdtPassword;

    @NonNull
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initComponent() {
        DaggerActivityComponent.builder().appComponent(getAppComponent()).build().inject(this);
    }

    @Override
    protected void onViewCreated(@Nullable Bundle savedInstanceState) {

    }

    @OnClick(R.id.login)
    void onClickLogin() {
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }

    @OnClick(R.id.logon)
    void onClickLogon(View view) {

    }

    @OnClick(R.id.forget_password)
    void onClickForgetPassword(View view) {

    }
}
