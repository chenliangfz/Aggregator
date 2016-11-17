package com.aggregator.card.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.aggregator.card.R;
import com.aggregator.card.core.BaseActivity;
import com.aggregator.card.core.inject.component.DaggerActivityComponent;
import com.aggregator.card.model.UserModel;
import com.aggregator.card.ui.activity.account.LoginActivity;

import javax.inject.Inject;

/**
 * Created by ChenLiang on 16/11/13.
 */

public class LaunchActivity extends BaseActivity {

    @Inject
    UserModel mUserModel;

    @NonNull
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_launcher;
    }

    @Override
    protected void initComponent() {
        DaggerActivityComponent.builder().appComponent(getAppComponent()).build().inject(this);
    }

    @Override
    protected void onViewCreated(@Nullable Bundle savedInstanceState) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mUserModel.isValid()) {
                    startActivity(new Intent(LaunchActivity.this, MainActivity.class));
                } else {
                    startActivity(new Intent(LaunchActivity.this, LoginActivity.class));
                }
                finish();
            }
        }, 2 * 1000L);
    }

    @Override
    public void onBackPressed() {
    }
}
