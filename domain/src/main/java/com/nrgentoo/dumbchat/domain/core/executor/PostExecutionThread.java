package com.nrgentoo.dumbchat.domain.core.executor;

import com.nrgentoo.dumbchat.domain.core.usecase.SingleUseCase;
import io.reactivex.Scheduler;

/**
 * Thread where {@link SingleUseCase} result should be delivered
 */

public interface PostExecutionThread {

    Scheduler getScheduler();
}
