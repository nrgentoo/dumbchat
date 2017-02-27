package com.nrgentoo.dumbchat.presentation.features.chat.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.nrgentoo.dumbchat.domain.features.chat.ChatService;
import com.nrgentoo.dumbchat.presentation.DumbChatApplication;
import com.nrgentoo.dumbchat.presentation.core.injection.application.ApplicationComponent;
import com.nrgentoo.dumbchat.presentation.core.injection.service.DaggerServiceComponent;
import com.nrgentoo.dumbchat.presentation.core.injection.service.ServiceComponent;
import com.nrgentoo.dumbchat.presentation.core.injection.service.ServiceModule;

import javax.inject.Inject;

/**
 * Service to run chat bot
 */

public class DumbChatService extends Service {

    private ServiceComponent mServiceComponent;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, DumbChatService.class);
    }

    @Inject
    ChatService mChatService;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        getServiceComponent().inject(this);

        mChatService.connect();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mChatService.disconnect();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    private ServiceComponent getServiceComponent() {
        if (mServiceComponent == null) {
            ApplicationComponent applicationComponent = DumbChatApplication.get(this)
                    .getApplicationComponent();

            mServiceComponent = DaggerServiceComponent.builder()
                    .applicationComponent(applicationComponent)
                    .serviceModule(new ServiceModule(this))
                    .build();
        }

        return mServiceComponent;
    }
}
