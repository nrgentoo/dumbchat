package com.nrgentoo.dumbchat.presentation.features.chat.data;

import com.google.auto.value.AutoValue;

/**
 * Chat photo View Model
 */

@AutoValue
public abstract class ChatPhotoVM {

    public abstract long id();

    public abstract String uri();

    public static Builder builder() {
        return new AutoValue_ChatPhotoVM.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {

        public abstract Builder setId(long id);

        public abstract Builder setUri(String uri);

        public abstract ChatPhotoVM build();
    }
}
