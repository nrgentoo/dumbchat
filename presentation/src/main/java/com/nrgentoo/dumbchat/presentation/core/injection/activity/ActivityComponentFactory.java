package com.nrgentoo.dumbchat.presentation.core.injection.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.nrgentoo.dumbchat.presentation.DumbChatApplication;
import com.nrgentoo.dumbchat.presentation.core.injection.application.ApplicationComponent;
import com.nrgentoo.dumbchat.presentation.core.injection.configpersistent.ConfigPersistentComponent;
import com.nrgentoo.dumbchat.presentation.core.injection.configpersistent.DaggerConfigPersistentComponent;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Factory to build {@link ActivityComponent}
 */

public class ActivityComponentFactory {

    private static final String KEY_ACTIVITY_ID = "KEY_ACTIVITY_ID";
    private static final AtomicLong NEXT_ID = new AtomicLong(0);
    @SuppressLint("UseSparseArrays")
    private static final Map<Long, ConfigPersistentComponent> sComponentsMap = new HashMap<>();

    private ActivityComponent mActivityComponent;
    private long mActivityId;

    public void build(Activity activity, Bundle savedInstanceState) {
        mActivityId = savedInstanceState != null ?
                savedInstanceState.getLong(KEY_ACTIVITY_ID) : NEXT_ID.getAndIncrement();

        ConfigPersistentComponent configPersistentComponent = getConfigPersistentComponent(activity);
        mActivityComponent = configPersistentComponent
                .plusActivityComponent(new ActivityModule(activity));
    }

    public void save(Bundle outState) {
        outState.putLong(KEY_ACTIVITY_ID, mActivityId);
    }

    public void destroy(Activity activity) {
        if (!activity.isChangingConfigurations()) {
            sComponentsMap.remove(mActivityId);
        }
    }

    public ActivityComponent getActivityComponent() {
        return mActivityComponent;
    }

    private ConfigPersistentComponent getConfigPersistentComponent(Context context) {
        ConfigPersistentComponent configPersistentComponent;
        if (!sComponentsMap.containsKey(mActivityId)) {
            configPersistentComponent = DaggerConfigPersistentComponent.builder()
                    .applicationComponent(getAppComponent(context))
                    .build();
            sComponentsMap.put(mActivityId, configPersistentComponent);
        } else {
            configPersistentComponent = sComponentsMap.get(mActivityId);
        }

        return configPersistentComponent;
    }

    private ApplicationComponent getAppComponent(Context context) {
        return DumbChatApplication.get(context).getApplicationComponent();
    }
}
