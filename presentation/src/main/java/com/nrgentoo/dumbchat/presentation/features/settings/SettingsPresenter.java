package com.nrgentoo.dumbchat.presentation.features.settings;

import com.nrgentoo.dumbchat.domain.features.settings.usecase.GetNotificationsSettingUseCase;
import com.nrgentoo.dumbchat.domain.features.settings.usecase.SetNotificationsSettingUseCase;
import com.nrgentoo.dumbchat.presentation.core.injection.configpersistent.ConfigPersistent;
import com.nrgentoo.dumbchat.presentation.core.ui.BasePresenter;

import javax.inject.Inject;
import javax.inject.Provider;

import io.reactivex.observers.DisposableSingleObserver;

/**
 * Presenter for {@link SettingsView}
 */

@ConfigPersistent
public class SettingsPresenter extends BasePresenter<SettingsView> {

    @Inject
    GetNotificationsSettingUseCase mGetNotificationsSettingUseCase;

    @Inject
    Provider<SetNotificationsSettingUseCase> mSetNotificationsSettingUseCaseProvider;

    @Inject
    public SettingsPresenter() {
    }

    @Override
    public void attachView(SettingsView mvpView) {
        super.attachView(mvpView);

        mGetNotificationsSettingUseCase.execute(new GetNotificationSettingObserver(), null);
    }

    @Override
    public void detachView() {
        super.detachView();

        mGetNotificationsSettingUseCase.unsubscribe();
    }

    private class GetNotificationSettingObserver extends DisposableSingleObserver<Boolean> {

        @Override
        public void onSuccess(Boolean aBoolean) {
            getMvpView().showNotificationSetting(aBoolean);
        }

        @Override
        public void onError(Throwable e) {

        }
    }

    public void onNotificationSettingChecked(boolean isChecked) {
        SetNotificationsSettingUseCase useCase = mSetNotificationsSettingUseCaseProvider.get();

        useCase.execute(new SetNotificationsSettingObserver(useCase), isChecked);
    }

    private class SetNotificationsSettingObserver extends DisposableSingleObserver<Boolean> {

        private final SetNotificationsSettingUseCase mUseCase;

        private SetNotificationsSettingObserver(SetNotificationsSettingUseCase useCase) {
            this.mUseCase = useCase;
        }

        @Override
        public void onSuccess(Boolean aBoolean) {
            mUseCase.unsubscribe();
        }

        @Override
        public void onError(Throwable e) {
            mUseCase.unsubscribe();
        }
    }
}
