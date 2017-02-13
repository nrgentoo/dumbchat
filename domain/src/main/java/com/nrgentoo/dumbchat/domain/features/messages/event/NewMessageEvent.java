package com.nrgentoo.dumbchat.domain.features.messages.event;

import com.google.auto.value.AutoValue;
import com.nrgentoo.dumbchat.domain.core.event.Event;

/**
 * Event for new message
 */

@AutoValue
public abstract class NewMessageEvent implements Event {

    public abstract long messageId();

    public static Builder builder() {
        return new AutoValue_NewMessageEvent.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {

        public abstract Builder setMessageId(long messageId);

        public abstract NewMessageEvent build();
    }
}
