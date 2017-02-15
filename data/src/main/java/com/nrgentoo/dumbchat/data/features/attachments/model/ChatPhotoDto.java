package com.nrgentoo.dumbchat.data.features.attachments.model;

import com.google.auto.value.AutoValue;

/**
 * Chat photo data transfer object
 */

@AutoValue
public abstract class ChatPhotoDto {

    public abstract long id();

    public abstract String uri();

    public static Builder builder() {
        return new AutoValue_ChatPhotoDto.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {

        public abstract Builder setId(long id);

        public abstract Builder setUri(String uri);

        public abstract ChatPhotoDto build();
    }
}
