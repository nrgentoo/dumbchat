package com.nrgentoo.dumbchat.domain.core.usecase;

import com.nrgentoo.dumbchat.domain.core.executor.PostExecutionThread;
import com.nrgentoo.dumbchat.domain.core.executor.ThreadExecutor;

import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Abstract use case which utilizes {@link Single}
 */

public abstract class SingleUseCase<R, P> {

    private Disposable mDisposable;

    private final ThreadExecutor mThreadExecutor;
    private final PostExecutionThread mPostExecutionThread;

    public SingleUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        this.mThreadExecutor = threadExecutor;
        this.mPostExecutionThread = postExecutionThread;
    }

    protected abstract Single<R> buildSingle(P params);

    /**
     * Execute use case with observer
     *
     * @param observer result observer
     * @param params parameters
     */
    public void execute(DisposableSingleObserver<R> observer, P params) {
        checkSubscription();

        mDisposable = buildSingle(params)
                .subscribeOn(Schedulers.from(mThreadExecutor))
                .observeOn(mPostExecutionThread.getScheduler())
                .subscribeWith(observer);
    }

    /**
     * Convenient method to chain use cases
     *
     * @param params parameters
     * @return {@link Single} of result
     */
    public Single<R> execute(P params) {
        return buildSingle(params);
    }

    /**
     * Unsubscribe from use case
     */
    public void unsubscribe() {
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }

    /**
     * Check subscription before subscribing to this use case
     */
    private void checkSubscription() {
        if (mDisposable != null && !mDisposable.isDisposed()) {
            throw new IllegalStateException("FlowableUseCase " + getClass().getName() +
                    " is already executing");
        }
    }
}
