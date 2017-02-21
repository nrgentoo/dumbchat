package com.nrgentoo.dumbchat.domain.core.event;

import com.nrgentoo.dumbchat.presentation.core.injection.application.PerApplication;

import javax.inject.Inject;

/**
 * Rx {@link EventsPort} implementation
 */

@PerApplication
public class RxEventBus implements EventsPort {

    @Inject
    public RxEventBus() {
    }

    @Override
    public <T extends Event> void subscribe(Class<T> type, EventCallback<T> listener) {
        // TODO
    }

    @Override
    public <T extends Event> void unsubscribe(Class<T> type, EventCallback<T> listener) {
        // TODO
    }

    @Override
    public <T extends Event> void broadcast(Class<T> type, T event) {
        // TODO
    }
}
