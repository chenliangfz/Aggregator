package com.aggregator.card.ui.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.aggregator.card.R;
import com.aggregator.card.core.component.DaggerActivityComponent;
import com.aggregator.card.presenter.MainPresenter;
import com.aggregator.card.ui.view.MainView;

import javax.inject.Inject;


public class MainActivity extends BaseActivity implements MainView {

    @Inject
    MainPresenter mPresenter;

    @Override
    protected void initView() {
    }

    @Override
    protected CharSequence getDefaultTitle() {
        return getString(R.string.app_name);
    }

    @Override
    protected void initPresenter() {
        DaggerActivityComponent.builder().build().inject(this);
        mPresenter.attachView(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @NonNull
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }


    public static int REQUEST_QR_CODE = 0x0001;

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK
                && requestCode == REQUEST_QR_CODE
                && data != null) {
            String result = data.getStringExtra("result");
            Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
        }
    }
}
