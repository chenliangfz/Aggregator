package com.aggregator.card.ui.activity.account;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.aggregator.card.R;
import com.aggregator.card.core.mvp.extension.RxAppCompatActivityView;

/**
 * Created by ChenLiang on 16/11/13.
 */

public class LoginActivity extends RxAppCompatActivityView<LoginPresenter> {
    @NonNull
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initComponent() {

    }

    @Override
    protected void onViewCreated(@Nullable Bundle savedInstanceState) {

    }
}
