package com.nrgentoo.dumbchat.presentation.core.injection.application;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Annotation for application scope
 */

@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerApplication {
}
