package com.nrgentoo.dumbchat.presentation.features.settings;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.widget.SwitchCompat;
import android.view.MenuItem;

import com.nrgentoo.dumbchat.R;
import com.nrgentoo.dumbchat.presentation.core.ui.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Activity fro application settings
 */

public class SettingsActivity extends BaseActivity implements SettingsView {

    @Inject
    SettingsPresenter mPresenter;

    @BindView(R.id.sw_notifications)
    SwitchCompat swNotifications;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, SettingsActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);

        getActivityComponent().inject(this);
        initViews();

        mPresenter.attachView(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initViews() {
        swNotifications.setOnCheckedChangeListener((buttonView, isChecked) ->
                mPresenter.onNotificationSettingChecked(isChecked));
    }

    // --------------------------------------------------------------------------------------------
    //      VIEW INTERFACE
    // --------------------------------------------------------------------------------------------

    @Override
    public void showNotificationSetting(Boolean enabled) {
        swNotifications.setOnCheckedChangeListener(null);

        swNotifications.setChecked(enabled);

        swNotifications.setOnCheckedChangeListener((buttonView, isChecked) ->
                mPresenter.onNotificationSettingChecked(isChecked));
    }
}
