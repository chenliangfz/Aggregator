package com.aggregator.card.core.inject.component;


import com.aggregator.card.core.inject.module.ApiModule;
import com.aggregator.card.core.inject.module.AppModule;
import com.aggregator.card.model.UserModel;
import com.aggregator.card.source.SourceManager;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, ApiModule.class})
public interface AppComponent {

    SourceManager getSourceManager();

    UserModel getUserModel();
}
