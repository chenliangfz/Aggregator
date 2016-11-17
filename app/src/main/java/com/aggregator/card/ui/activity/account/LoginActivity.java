package com.aggregator.card.ui.activity.account;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.aggregator.card.R;
import com.aggregator.card.core.inject.component.DaggerActivityComponent;
import com.aggregator.card.core.mvp.extension.RxAppCompatActivityView;
import com.aggregator.card.mock.Mocks;
import com.aggregator.card.model.UserModel;
import com.aggregator.card.ui.activity.MainActivity;
import com.aggregator.card.util.L;
import com.aggregator.card.util.StringUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;

/**
 * Created by ChenLiang on 16/11/13.
 */

public class LoginActivity extends RxAppCompatActivityView<LoginPresenter> {


    @BindView(R.id.account)
    EditText mEdtAccount;

    @BindView(R.id.password)
    EditText mEdtPassword;

    @Inject
    UserModel mUserModel;

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
        String account = mEdtAccount.getText().toString();
        String password = mEdtPassword.getText().toString();
        if (TextUtils.equals(account, Mocks.USER_ACCOUNT) && TextUtils.equals(password,Mocks.USER_PASSWORD)) {
            mUserModel.account = account;
            mUserModel.password = password;
            mUserModel.saveCache(this);
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }else{
            Toast.makeText(this,"账号无效!",Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.logon)
    void onClickLogon(View view) {

    }

    @OnClick(R.id.forget_password)
    void onClickForgetPassword(View view) {

    }
}
