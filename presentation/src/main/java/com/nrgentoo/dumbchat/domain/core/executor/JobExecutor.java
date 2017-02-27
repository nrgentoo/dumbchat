package com.nrgentoo.dumbchat.domain.core.executor;

import android.support.annotation.NonNull;

import com.nrgentoo.dumbchat.presentation.core.injection.application.PerApplication;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

/**
 * Decorated {@link java.util.concurrent.ThreadPoolExecutor}
 */

@PerApplication
public class JobExecutor implements ThreadExecutor {

    private final ThreadPoolExecutor mThreadPoolExecutor;

    @Inject
    public JobExecutor() {
        mThreadPoolExecutor = new ThreadPoolExecutor(3, 5, 10, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(), new JobThreadFactory());
    }

    @Override
    public void execute(@NonNull Runnable command) {
        mThreadPoolExecutor.execute(command);
    }

    private static class JobThreadFactory implements ThreadFactory {
        private int counter = 0;

        @Override public Thread newThread(@NonNull Runnable runnable) {
            return new Thread(runnable, "android_" + counter++);
        }
    }
}
