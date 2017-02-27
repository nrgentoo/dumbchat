package com.nrgentoo.dumbchat.presentation.core.injection.activity;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Annotation for activity scope
 */

@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerActivity {
}
