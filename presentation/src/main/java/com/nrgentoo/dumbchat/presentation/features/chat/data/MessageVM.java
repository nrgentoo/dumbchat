package com.nrgentoo.dumbchat.presentation.features.chat.data;

import com.google.auto.value.AutoValue;

import java.util.List;

/**
 * View model of message
 */

@AutoValue
public abstract class MessageVM {

    public abstract long id();

    public abstract String text();

    public abstract UserVM author();

    public abstract boolean isMine();

    public abstract String dateTime();

    public abstract List<AttachmentVM<?>> attachments();

    public static Builder builder() {
        return new AutoValue_MessageVM.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {

        public abstract Builder setId(long id);

        public abstract Builder setText(String text);

        public abstract Builder setAuthor(UserVM author);

        public abstract Builder setIsMine(boolean isMine);

        public abstract Builder setDateTime(String dateTime);

        public abstract Builder setAttachments(List<AttachmentVM<?>> attachments);

        public abstract MessageVM build();
    }
}
