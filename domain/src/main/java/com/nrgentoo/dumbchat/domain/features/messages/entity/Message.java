package com.nrgentoo.dumbchat.domain.features.messages.entity;

import com.google.auto.value.AutoValue;
import com.nrgentoo.dumbchat.domain.features.attachments.entity.Attachment;
import com.nrgentoo.dumbchat.domain.features.users.entity.User;

import java.util.Collections;
import java.util.List;

import javax.annotation.Nullable;

/**
 * Message entity. {@link Message#text()} and {@link #attachments()} can't be both null or empty
 */

@AutoValue
public abstract class Message {

    public abstract long id();

    @Nullable
    public abstract String text();

    public abstract User author();

    public abstract long timeStamp();

    public abstract List<Attachment<?>> attachments();

    public static Builder builder() {
        return new AutoValue_Message.Builder()
                .setAttachments(Collections.emptyList());
    }

    @AutoValue.Builder
    public abstract static class Builder {

        public abstract Builder setId(long id);

        public abstract Builder setText(@Nullable String text);

        public abstract Builder setAuthor(User author);

        public abstract Builder setTimeStamp(long timeStamp);

        public abstract Builder setAttachments(List<Attachment<?>> attachments);

        public abstract Message build();
    }
}
