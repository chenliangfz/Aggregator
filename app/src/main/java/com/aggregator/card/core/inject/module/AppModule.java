package com.aggregator.card.core.inject.module;

import android.content.Context;

import com.aggregator.card.core.App;
import com.aggregator.card.model.UserModel;
import com.aggregator.card.source.SourceManager;
import com.aggregator.card.source.greendao.DaoMaster;
import com.aggregator.card.source.greendao.DaoSession;
import com.aggregator.card.source.network.ApiService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

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
    
    @Singleton
    @Provides
    UserModel provideUserModel(Context context){
        return new UserModel().getCache(context);
    }

    @Singleton
    @Provides
    DaoSession provideDaoSession(Context context){
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(context, "cardAggregator-db", null);
        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
        return daoMaster.newSession();
    }
}
