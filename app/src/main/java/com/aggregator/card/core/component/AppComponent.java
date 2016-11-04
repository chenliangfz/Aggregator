package com.aggregator.card.core.component;


import com.aggregator.card.core.module.ApiModule;
import com.aggregator.card.core.module.AppModule;
import com.aggregator.card.source.SourceManager;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, ApiModule.class})
public interface AppComponent {

    SourceManager getSourceManager();
}
