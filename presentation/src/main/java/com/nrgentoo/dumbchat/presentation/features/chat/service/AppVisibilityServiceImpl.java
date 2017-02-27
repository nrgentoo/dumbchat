package com.nrgentoo.dumbchat.presentation.features.chat.service;

import com.nrgentoo.dumbchat.domain.features.chat.AppVisibilityService;

import javax.inject.Inject;

/**
 * Service to check if app in foreground or in backgriund
 */

public class AppVisibilityServiceImpl implements AppVisibilityService {

    @Inject
    public AppVisibilityServiceImpl() {
    }

    @Override
    public boolean isInBackground() {
        // TODO
        return false;
    }
}
