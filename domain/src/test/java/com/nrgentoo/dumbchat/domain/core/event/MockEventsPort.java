package com.nrgentoo.dumbchat.domain.core.event;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * {@link EventsPort} for tests
 */

public class MockEventsPort implements EventsPort {

    private final Map<Class, List<EventCallback<Event>>> mListenersMap;

    public MockEventsPort() {
        mListenersMap = new HashMap<>();
    }

    @Override
    public <T extends Event> void subscribe(Class<T> type, EventCallback<T> listener) {
        //noinspection unchecked
        getCallbacksList(type).add((EventCallback<Event>) listener);
    }

    @Override
    public <T extends Event> void unsubscribe(Class<T> type, EventCallback<T> listener) {
        //noinspection SuspiciousMethodCalls
        getCallbacksList(type).remove(listener);
    }

    @Override
    public <T extends Event> void broadcast(Class<T> type, T event) {
        List<EventCallback<Event>> callbacks = getCallbacksList(type);
        if (callbacks != null) {
            for (EventCallback<Event> callback : callbacks) {
                callback.call(event);
            }
        }
    }

    public List<EventCallback<Event>> getCallbacksList(Class type) {
        //noinspection Java8CollectionsApi
        if (mListenersMap.get(type) == null) {
            mListenersMap.put(type, new LinkedList<>());
        }

        return mListenersMap.get(type);
    }

    public <T extends Event> boolean isSubscribed(Class<T> type, EventCallback<T> listener) {
        List<EventCallback<Event>> callbacks = getCallbacksList(type);
        //noinspection SuspiciousMethodCalls
        return callbacks != null && callbacks.contains(listener);
    }
}
