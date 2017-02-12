package com.nrgentoo.dumbchat.domain.features.attachments.entity;

import com.google.auto.value.AutoValue;

/**
 * Photo entity
 */

@AutoValue
public abstract class ChatPhoto {

    public abstract long id();

    public abstract String uri();

    public static Builder builder() {
        return new AutoValue_ChatPhoto.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {

        public abstract Builder setId(long id);

        public abstract Builder setUri(String uri);

        public abstract ChatPhoto build();
    }
}
