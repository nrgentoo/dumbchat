package com.nrgentoo.dumbchat.domain.core.event;

/**
 * Interface for event bus
 */

public interface EventsPort {

    /**
     * Subscribe to events of type T
     *
     * @param type     event class to listen
     * @param listener callback which will be called when event will be received
     * @param <T>      event type
     */
    <T extends Event> void subscribe(Class<T> type, final EventCallback<T> listener);

    /**
     * Unsubscribe listener
     *
     * @param type     event class
     * @param listener listener to unsubscribe
     * @param <T>      event type
     */
    <T extends Event> void unsubscribe(Class<T> type, final EventCallback<T> listener);

    /**
     * Broadcast event to all listeners
     */
    <T extends Event> void broadcast(Class<T> type, T event);
}
