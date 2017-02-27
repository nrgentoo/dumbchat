package com.nrgentoo.dumbchat.presentation.features.chat.data;

import com.google.auto.value.AutoValue;

/**
 * Attachment View Model
 */

@AutoValue
public abstract class AttachmentVM<T> {

    public abstract long id();

    public abstract long messageId();

    public abstract T attachment();

    public abstract String type();

    public static Builder builder() {
        return new AutoValue_AttachmentVM.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder<T> {

        public abstract Builder<T> setId(long id);

        public abstract Builder<T> setMessageId(long messageId);

        public abstract Builder<T> setAttachment(T attachment);

        public abstract Builder<T> setType(String type);

        public abstract AttachmentVM<T> build();
    }
}
