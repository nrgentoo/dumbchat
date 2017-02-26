package com.nrgentoo.dumbchat.domain.core.event;

import com.nrgentoo.dumbchat.presentation.core.injection.application.PerApplication;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;

/**
 * Rx {@link EventsPort} implementation
 */

@PerApplication
public class RxEventBus implements EventsPort {

    private final PublishSubject<Event> bus = PublishSubject.create();

    private Map<EventCallback, Disposable> mSubscriptions = new LinkedHashMap<>();

    @Inject
    public RxEventBus() {
    }

    @Override
    public <T extends Event> void subscribe(Class<T> type, EventCallback<T> listener) {
        //noinspection unchecked
        Disposable d = bus.subscribe(event -> listener.call((T) event));
        mSubscriptions.put(listener, d);
    }

    @Override
    public <T extends Event> void unsubscribe(Class<T> type, EventCallback<T> listener) {
        Disposable d = mSubscriptions.get(listener);
        if (d != null) {
            d.dispose();
            mSubscriptions.remove(listener);
        }
    }

    @Override
    public <T extends Event> void broadcast(Class<T> type, T event) {
        bus.onNext(event);
    }
}
