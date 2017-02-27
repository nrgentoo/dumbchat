package com.nrgentoo.dumbchat.presentation.core.ui;

/**
 * Interface for presenter in MVP
 */

public interface Presenter<V extends MvpView> {

    void attachView(V mvpView);

    void detachView();
}
