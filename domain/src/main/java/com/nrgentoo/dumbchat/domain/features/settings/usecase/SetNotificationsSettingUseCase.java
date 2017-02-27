package com.nrgentoo.dumbchat.domain.features.settings.usecase;

import com.nrgentoo.dumbchat.domain.core.executor.PostExecutionThread;
import com.nrgentoo.dumbchat.domain.core.executor.ThreadExecutor;
import com.nrgentoo.dumbchat.domain.core.usecase.SingleUseCase;
import com.nrgentoo.dumbchat.domain.features.settings.SettingsService;

import javax.inject.Inject;

import io.reactivex.Single;

/**
 * Use case to set notifications setting
 */

public class SetNotificationsSettingUseCase extends SingleUseCase<Boolean, Boolean> {

    @Inject
    SettingsService mSettingsService;

    @Inject
    public SetNotificationsSettingUseCase(ThreadExecutor threadExecutor,
                                          PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
    }

    @Override
    protected Single<Boolean> buildSingle(Boolean enabled) {
        return Single.create(e -> {
            if (e.isDisposed()) return;

            mSettingsService.setNotificationsEnabled(enabled);

            e.onSuccess(enabled);
        });
    }
}
