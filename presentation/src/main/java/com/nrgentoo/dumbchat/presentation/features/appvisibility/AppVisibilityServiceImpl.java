package com.nrgentoo.dumbchat.presentation.features.appvisibility;

import android.content.Context;

import com.nrgentoo.dumbchat.domain.features.chat.AppVisibilityService;
import com.nrgentoo.dumbchat.presentation.DumbChatApplication;
import com.nrgentoo.dumbchat.presentation.core.injection.service.QServiceContext;

import javax.inject.Inject;

/**
 * Service to check if app in foreground or in backgriund
 */

public class AppVisibilityServiceImpl implements AppVisibilityService {

    @Inject
    @QServiceContext
    Context mContext;

    @Inject
    public AppVisibilityServiceImpl() {
    }

    @Override
    public boolean isInBackground() {
        return !DumbChatApplication.get(mContext).isAppVisible();
    }
}
