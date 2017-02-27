package com.nrgentoo.dumbchat.presentation.core.injection.service;

import android.app.Service;
import android.content.Context;

import com.nrgentoo.dumbchat.domain.features.chat.ChatService;
import com.nrgentoo.dumbchat.domain.features.chat.OfflineChatService;

import dagger.Module;
import dagger.Provides;

/**
 * Module for service component
 */

@Module
public class ServiceModule {

    private final Context mContext;

    public ServiceModule(Service service) {
        mContext = service;
    }

    @Provides
    @PerService
    @QServiceContext
    Context provideContext() {
        return mContext;
    }

    @Provides
    @PerService
    ChatService provideChatService(OfflineChatService chatService) {
        return chatService;
    }
}
