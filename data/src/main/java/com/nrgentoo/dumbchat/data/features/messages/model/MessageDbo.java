package com.nrgentoo.dumbchat.data.features.messages.model;

import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;

/**
 * Database object for message
 */

@AutoValue
public abstract class MessageDbo {

    public abstract long id();

    @Nullable
    public abstract String text();

    public abstract long authorId();

    public abstract long timeStamp();

    public static Builder builder() {
        return new AutoValue_MessageDbo.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {

        public abstract Builder setId(long id);

        public abstract Builder setText(@Nullable String text);

        public abstract Builder setAuthorId(long authorId);

        public abstract Builder setTimeStamp(long timeStamp);

        public abstract MessageDbo build();
    }
}
