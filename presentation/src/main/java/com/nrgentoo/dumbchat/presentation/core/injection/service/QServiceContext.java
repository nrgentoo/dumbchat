package com.nrgentoo.dumbchat.presentation.core.injection.service;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Qualifier for service context
 */

@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface QServiceContext {
}
