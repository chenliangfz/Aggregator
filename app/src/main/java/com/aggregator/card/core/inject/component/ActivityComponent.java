package com.aggregator.card.core.inject.component;


import com.aggregator.card.core.inject.scope.ActivityScope;
import com.aggregator.card.ui.activity.LaunchActivity;
import com.aggregator.card.ui.activity.MainActivity;
import com.aggregator.card.ui.activity.account.LoginActivity;
import com.aggregator.card.ui.activity.membership.AdditionActivity;

import dagger.Component;

@ActivityScope
@Component(dependencies = AppComponent.class)
public interface ActivityComponent {

    void inject(LaunchActivity launchActivity);

    void inject(LoginActivity loginActivity);

    void inject(MainActivity mainActivity);

    void inject(AdditionActivity additionActivity);

}
