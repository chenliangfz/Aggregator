package com.aggregator.card.core.mvp.extension;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.aggregator.card.core.App;
import com.aggregator.card.core.BaseActivity;
import com.aggregator.card.core.inject.component.AppComponent;
import com.aggregator.card.core.mvp.view.BaseView;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by ChenLiang on 16/11/4.
 */

public abstract class RxAppCompatActivityView<P extends RxAppcompatActivityPresenter> extends BaseActivity implements BaseView {

    @Inject
    protected P presenter;

    protected void onViewCreated(@Nullable Bundle savedInstanceState){
        presenter.onAttach(this);
    }

    @Override
    public void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    protected AppComponent getAppComponent() {
        return ((App) getApplication()).getAppComponent();
    }
}
