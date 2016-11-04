package com.aggregator.card.core.inject.component;


import com.aggregator.card.core.inject.scope.FragmentScope;

import dagger.Component;

@FragmentScope
@Component(dependencies = AppComponent.class)
public interface FragmentComponent {
}
