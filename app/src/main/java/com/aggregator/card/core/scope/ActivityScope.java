package com.aggregator.card.core.scope;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by ChenLiang on 16/10/12.
 */
@Scope
@Documented
@Retention(RUNTIME)
public @interface ActivityScope {
}
