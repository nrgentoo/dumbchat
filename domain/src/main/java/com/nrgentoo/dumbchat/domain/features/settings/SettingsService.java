package com.nrgentoo.dumbchat.domain.features.settings;

/**
 * Service to control application settings
 */

public interface SettingsService {

    boolean notificationsEnabled();

    void setNotificationsEnabled(boolean enabled);
}
