package com.nrgentoo.dumbchat.domain.core.event;

/**
 * Event callback
 */

public interface EventCallback<T extends Event> {

    void call(T event);
}
