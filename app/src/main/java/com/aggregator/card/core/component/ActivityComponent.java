package com.aggregator.card.core.component;


import com.aggregator.card.core.scope.ActivityScope;
import com.aggregator.card.ui.activity.MainActivity;

import dagger.Component;

/**
 * Created by ChenLiang on 16/10/12.
 */
@ActivityScope
@Component(dependencies = AppComponent.class)
public interface ActivityComponent {

    void inject(MainActivity mainActivity);

}
