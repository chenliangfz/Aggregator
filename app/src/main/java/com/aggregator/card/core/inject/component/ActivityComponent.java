package com.aggregator.card.core.inject.component;


import com.aggregator.card.core.inject.scope.ActivityScope;
import com.aggregator.card.ui.activity.MainActivity;

import dagger.Component;

@ActivityScope
@Component(dependencies = AppComponent.class)
public interface ActivityComponent {

    void inject(MainActivity mainActivity);

}
