package com.aggregator.card.core.module;

import android.content.Context;

import com.aggregator.card.core.App;
import com.aggregator.card.source.SourceManager;
import com.aggregator.card.source.network.ApiService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * 提供了AppComponent里的需要注入的对象, @Provides 类似 @Inject
 */
@Module
public class AppModule {

    private Context mApplicationContext;

    public AppModule(App application) {
        mApplicationContext = application.getApplicationContext();
    }

    @Provides
    Context provideApplicationContext() {
        return mApplicationContext;
    }

    @Singleton
    @Provides
    SourceManager provideSourceManager(ApiService apiService) {
        return new SourceManager(apiService);
    }
}
