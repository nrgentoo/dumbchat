package com.nrgentoo.dumbchat.presentation.core.injection.service;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Service scope annotation
 */

@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerService {
}
