package com.nrgentoo.dumbchat.domain.core.usecase;

import com.nrgentoo.dumbchat.domain.core.executor.PostExecutionThread;
import com.nrgentoo.dumbchat.domain.core.executor.ThreadExecutor;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

/**
 * External executor of use cases
 */

public class UseCaseExecutor {

    private Disposable mDisposable;

    private final ThreadExecutor mThreadExecutor;
    private final PostExecutionThread mPostExecutionThread;

    @Inject
    public UseCaseExecutor(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        this.mThreadExecutor = threadExecutor;
        this.mPostExecutionThread = postExecutionThread;
    }

    /**
     * Execute {@link Single<R>}
     *
     * @param single rx single to execute
     * @param observer result observer
     * @param <R> result type
     */
    public <R> void execute(Single<R> single, DisposableSingleObserver<R> observer) {
        checkSubscription();

        mDisposable = single
                .subscribeOn(Schedulers.from(mThreadExecutor))
                .observeOn(mPostExecutionThread.getScheduler())
                .subscribeWith(observer);
    }

    /**
     * Execute {@link Flowable<R>}
     *
     * @param flowable rx flowable to execute
     * @param subscriber result subscriber
     * @param <R> result type
     */
    public <R> void execute(Flowable<R> flowable, DisposableSubscriber<R> subscriber) {
        checkSubscription();

        mDisposable = flowable
                .subscribeOn(Schedulers.from(mThreadExecutor))
                .observeOn(mPostExecutionThread.getScheduler())
                .subscribeWith(subscriber);
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
            throw new IllegalStateException(getClass().getName() + " is already executing");
        }
    }
}
