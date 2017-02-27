package com.nrgentoo.dumbchat.presentation;

import android.app.Application;
import android.content.Context;

import com.nrgentoo.dumbchat.presentation.core.injection.application.ApplicationComponent;
import com.nrgentoo.dumbchat.presentation.core.injection.application.ApplicationModule;
import com.nrgentoo.dumbchat.presentation.core.injection.application.DaggerApplicationComponent;

/**
 * Application class
 */

public class DumbChatApplication extends Application {

    private ApplicationComponent mApplicationComponent;

    public static DumbChatApplication get(Context context) {
        return (DumbChatApplication) context.getApplicationContext();
    }

    public ApplicationComponent getApplicationComponent() {
        if (mApplicationComponent == null) {
            mApplicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this))
                    .build();
        }

        return mApplicationComponent;
    }
}
