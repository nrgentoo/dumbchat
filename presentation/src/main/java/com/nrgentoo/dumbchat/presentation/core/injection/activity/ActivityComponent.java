package com.nrgentoo.dumbchat.presentation.core.injection.activity;

import com.nrgentoo.dumbchat.presentation.features.chat.ui.ChatActivity;
import com.nrgentoo.dumbchat.presentation.features.settings.SettingsActivity;

import dagger.Subcomponent;

/**
 * Activity dagger component
 */

@PerActivity
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(ChatActivity chatActivity);

    void inject(SettingsActivity settingsActivity);
}
