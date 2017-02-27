package com.nrgentoo.dumbchat.presentation.features.settings;

import com.nrgentoo.dumbchat.presentation.core.ui.MvpView;

/**
 * View interface of application settings
 */

public interface SettingsView extends MvpView {

    void showNotificationSetting(Boolean enabled);
}
