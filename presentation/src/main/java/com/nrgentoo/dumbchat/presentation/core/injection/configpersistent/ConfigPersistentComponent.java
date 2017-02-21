package com.nrgentoo.dumbchat.presentation.core.injection.configpersistent;

import com.nrgentoo.dumbchat.presentation.core.injection.activity.ActivityComponent;
import com.nrgentoo.dumbchat.presentation.core.injection.activity.ActivityModule;
import com.nrgentoo.dumbchat.presentation.core.injection.application.ApplicationComponent;

import dagger.Component;

/**
 * Config persistent dagger component
 */

@ConfigPersistent
@Component(dependencies = ApplicationComponent.class)
public interface ConfigPersistentComponent {

    ActivityComponent plusActivityComponent(ActivityModule module);
}
