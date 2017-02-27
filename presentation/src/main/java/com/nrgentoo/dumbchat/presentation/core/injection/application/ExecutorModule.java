package com.nrgentoo.dumbchat.presentation.core.injection.application;

import com.nrgentoo.dumbchat.domain.core.executor.JobExecutor;
import com.nrgentoo.dumbchat.domain.core.executor.PostExecutionThread;
import com.nrgentoo.dumbchat.domain.core.executor.ThreadExecutor;
import com.nrgentoo.dumbchat.domain.core.executor.UIThread;

import dagger.Module;
import dagger.Provides;

/**
 * Module for providing use case executors
 */

@Module
public class ExecutorModule {

    @Provides
    @PerApplication
    ThreadExecutor provideThreadExecutor(JobExecutor executor) {
        return executor;
    }

    @Provides
    @PerApplication
    PostExecutionThread providePostExecutionThread(UIThread uiThread) {
        return uiThread;
    }
}
