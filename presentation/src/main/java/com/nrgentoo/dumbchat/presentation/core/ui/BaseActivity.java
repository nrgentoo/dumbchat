package com.nrgentoo.dumbchat.presentation.core.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.nrgentoo.dumbchat.presentation.core.injection.activity.ActivityComponent;
import com.nrgentoo.dumbchat.presentation.core.injection.activity.ActivityComponentFactory;

/**
 * Base activity
 */

public abstract class BaseActivity extends AppCompatActivity {

    private ActivityComponentFactory mActivityComponentFactory = new ActivityComponentFactory();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityComponentFactory.build(this, savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mActivityComponentFactory.save(outState);
    }

    @Override
    protected void onDestroy() {
        mActivityComponentFactory.destroy(this);
        super.onDestroy();
    }

    protected ActivityComponent getActivityComponent() {
        return mActivityComponentFactory.getActivityComponent();
    }
}
