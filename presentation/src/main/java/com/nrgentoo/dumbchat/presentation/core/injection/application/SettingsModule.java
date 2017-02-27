package com.nrgentoo.dumbchat.presentation.core.injection.application;

import com.nrgentoo.dumbchat.data.features.settings.SettingsServiceImpl;
import com.nrgentoo.dumbchat.domain.features.settings.SettingsService;

import dagger.Module;
import dagger.Provides;

/**
 * Module with dependencies for settings
 */

@Module
public class SettingsModule {

    @Provides
    @PerApplication
    SettingsService provideSettingsService(SettingsServiceImpl settingsService) {
        return settingsService;
    }
}
