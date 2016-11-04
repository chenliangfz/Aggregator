package com.aggregator.card.core.component;


import com.aggregator.card.core.scope.FragmentScope;

import dagger.Component;

/**
 * Created by ChenLiang on 16/10/12.
 */
@FragmentScope
@Component(dependencies = AppComponent.class)
public interface FragmentComponent {
}
