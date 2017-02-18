package com.nrgentoo.dumbchat.domain.core.executor;

import java.util.concurrent.Executor;

/**
 * Every implementation should execute use case out of the UI thread
 */

public interface ThreadExecutor extends Executor {
}
