package com.nrgentoo.dumbchat.presentation.core.injection.application;

import android.content.Context;

import com.nrgentoo.dumbchat.domain.core.event.EventsPort;
import com.nrgentoo.dumbchat.domain.core.event.RxEventBus;

import dagger.Module;
import dagger.Provides;

/**
 * Application dagger module
 */
@Module
public class ApplicationModule {

    private final Context mContext;

    public ApplicationModule(Context context) {
        this.mContext = context;
    }

    @Provides
    Context provideContext() {
        return mContext;
    }

    @Provides
    @PerApplication
    EventsPort provideEventsPort(RxEventBus rxEventBus) {
        return rxEventBus;
    }
}
