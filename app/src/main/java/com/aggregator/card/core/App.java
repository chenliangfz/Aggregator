package com.aggregator.card.core;

import android.app.Application;

import com.aggregator.card.core.inject.component.AppComponent;
import com.aggregator.card.core.inject.component.DaggerAppComponent;
import com.aggregator.card.core.inject.module.AppModule;

/**
 * Created by ChenLiang on 16/11/4.
 */

public class App extends Application {

    AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }

}
