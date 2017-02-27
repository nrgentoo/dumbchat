package com.nrgentoo.dumbchat.presentation.core.injection.application;

import android.content.Context;

import com.nrgentoo.dumbchat.domain.core.event.EventsPort;
import com.nrgentoo.dumbchat.domain.core.executor.PostExecutionThread;
import com.nrgentoo.dumbchat.domain.core.executor.ThreadExecutor;
import com.nrgentoo.dumbchat.domain.core.repository.UnitOfWork;
import com.nrgentoo.dumbchat.domain.features.messages.repository.MessageRepo;
import com.nrgentoo.dumbchat.domain.features.settings.SettingsService;
import com.nrgentoo.dumbchat.domain.features.users.repository.UserRepo;

import dagger.Component;

/**
 * Application dagger component
 */

@PerApplication
@Component(modules = {
        ApplicationModule.class,
        RepositoryModule.class,
        DbModule.class,
        ExecutorModule.class,
        SettingsModule.class})
public interface ApplicationComponent {

    MessageRepo messageRepo();

    UserRepo userRepo();

    UnitOfWork unitOfWork();

    EventsPort eventsPort();

    ThreadExecutor threadExecutor();

    PostExecutionThread postExecutionThread();

    Context context();

    SettingsService settingsService();
}
