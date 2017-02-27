package com.nrgentoo.dumbchat.domain.features.settings.usecase;

import com.nrgentoo.dumbchat.domain.core.executor.PostExecutionThread;
import com.nrgentoo.dumbchat.domain.core.executor.ThreadExecutor;
import com.nrgentoo.dumbchat.domain.core.usecase.SingleUseCase;
import com.nrgentoo.dumbchat.domain.features.settings.SettingsService;

import javax.inject.Inject;

import io.reactivex.Single;

/**
 * Use case to get notification setting
 */

public class GetNotificationsSettingUseCase extends SingleUseCase<Boolean, Void> {

    @Inject
    SettingsService mSettingsService;

    @Inject
    public GetNotificationsSettingUseCase(ThreadExecutor threadExecutor,
                                          PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
    }

    @Override
    protected Single<Boolean> buildSingle(Void params) {
        return Single.just(mSettingsService.notificationsEnabled());
    }
}
