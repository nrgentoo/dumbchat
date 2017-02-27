package com.nrgentoo.dumbchat.data.features.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.nrgentoo.dumbchat.domain.features.settings.SettingsService;

import javax.inject.Inject;

/**
 * {@link SettingsService} implementation with shared preferences
 */

public class SettingsServiceImpl implements SettingsService {

    private static final String PREF_NOTIFICATIONS_SETTING = "notifications_setting";

    private final SharedPreferences mPrefs;

    @Inject
    public SettingsServiceImpl(Context context) {
        mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Override
    public boolean notificationsEnabled() {
        return mPrefs.getBoolean(PREF_NOTIFICATIONS_SETTING, false);
    }

    @Override
    public void setNotificationsEnabled(boolean enabled) {
        mPrefs.edit()
                .putBoolean(PREF_NOTIFICATIONS_SETTING, enabled)
                .apply();
    }
}
