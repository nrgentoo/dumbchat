package com.nrgentoo.dumbchat.data.features.attachments.chatphoto.model;

import com.google.auto.value.AutoValue;

/**
 * Chat photo data transfer object
 */

@AutoValue
public abstract class ChatPhotoDbo {

    public abstract long id();

    public abstract String uri();

    public static Builder builder() {
        return new AutoValue_ChatPhotoDbo.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {

        public abstract Builder setId(long id);

        public abstract Builder setUri(String uri);

        public abstract ChatPhotoDbo build();
    }
}
