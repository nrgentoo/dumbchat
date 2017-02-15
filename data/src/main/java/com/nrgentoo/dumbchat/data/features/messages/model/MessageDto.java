package com.nrgentoo.dumbchat.data.features.messages.model;

import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.nrgentoo.dumbchat.data.features.attachments.model.AttachmentDto;
import com.nrgentoo.dumbchat.data.features.users.model.UserDto;

import java.util.Collections;
import java.util.List;

/**
 * Message data transfer object
 */

@AutoValue
public abstract class MessageDto {

    @Nullable
    public abstract String text();

    public abstract UserDto author();

    public abstract long timeStamp();

    public abstract List<AttachmentDto<?>> attachments();

    public static Builder builder() {
        return new AutoValue_MessageDto.Builder()
                .setAttachments(Collections.emptyList());
    }

    @AutoValue.Builder
    public abstract static class Builder {

        public abstract Builder setText(@Nullable String text);

        public abstract Builder setAuthor(UserDto author);

        public abstract Builder setTimeStamp(long timeStamp);

        public abstract Builder setAttachments(List<AttachmentDto<?>> attachments);

        public abstract MessageDto build();
    }
}
