package com.nrgentoo.dumbchat.presentation.features.appvisibility;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

/**
 * Handles lifecycle of all activities in application
 */

public class ActivityLifecycleHandler implements Application.ActivityLifecycleCallbacks {

    private int mStarted;
    private int mStopped;

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
    }

    @Override
    public void onActivityStarted(Activity activity) {
        ++mStarted;
    }

    @Override
    public void onActivityResumed(Activity activity) {
    }

    @Override
    public void onActivityPaused(Activity activity) {
    }

    @Override
    public void onActivityStopped(Activity activity) {
        ++mStopped;
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
    }

    public boolean isVisible() {
        return mStarted > mStopped;
    }
}
