package com.nrgentoo.dumbchat.domain.core.usecase;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import com.nrgentoo.dumbchat.domain.core.executor.*;

/**
 * Abstract use case
 *
 * @param <R> result type
 * @param <P> parameter type. Can be {@link Void}
 */
public abstract class UseCase<R, P> {

    private Disposable mDisposable;

    private final ThreadExecutor mThreadExecutor;
    private final PostExecutionThread mPostExecutionThread;

    public UseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        this.mThreadExecutor = threadExecutor;
        this.mPostExecutionThread = postExecutionThread;
    }

    protected abstract Observable<R> buildObservable(P params);

    /**
     * Execute use case with observer
     *
     * @param observer result observer
     * @param params parameters
     */
    public void execute(DisposableObserver<R> observer, P params) {
        checkSubscription();

        mDisposable = buildObservable(params)
                .subscribeOn(Schedulers.from(mThreadExecutor))
                .observeOn(mPostExecutionThread.getScheduler())
                .subscribeWith(observer);
    }

    /**
     * Convenient method to chain use cases
     *
     * @param params parameters
     * @return {@link Observable} of result
     */
    public Observable<R> execute(P params) {
        return buildObservable(params);
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
            throw new IllegalStateException("UseCase " + getClass().getName() +
                    " is already executing");
        }
    }
}
