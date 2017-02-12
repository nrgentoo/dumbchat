package com.nrgentoo.dumbchat.domain.core.executor;

import com.nrgentoo.dumbchat.domain.core.usecase.UseCase;
import io.reactivex.Scheduler;

/**
 * Thread where {@link UseCase} result should be delivered
 */

public interface PostExecutionThread {

    Scheduler getScheduler();
}
