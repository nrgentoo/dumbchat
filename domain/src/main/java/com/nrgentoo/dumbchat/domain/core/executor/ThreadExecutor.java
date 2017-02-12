package com.nrgentoo.dumbchat.domain.core.executor;

import com.nrgentoo.dumbchat.domain.core.usecase.UseCase;
import java.util.concurrent.Executor;

/**
 * Every implementation should execute {@link UseCase} out of the UI thread
 */

public interface ThreadExecutor extends Executor {
}
