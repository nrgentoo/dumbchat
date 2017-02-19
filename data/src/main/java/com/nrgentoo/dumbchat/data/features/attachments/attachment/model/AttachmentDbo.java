package com.nrgentoo.dumbchat.data.features.attachments.attachment.model;

import com.google.auto.value.AutoValue;

/**
 * Database object for attachment
 */

@AutoValue
public abstract class AttachmentDbo {

    /**
     * Attachment id
     */
    public abstract long id();

    /**
     * Id of message to which attachment is belongs
     */
    public abstract long messageId();

    /**
     * Id of attached entity in some table. Table can be resolved through {@link #type()}
     */
    public abstract long entityId();

    /**
     * Type of attachment
     */
    public abstract String type();

    public static Builder builder() {
        return new AutoValue_AttachmentDbo.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {

        public abstract Builder setId(long id);

        public abstract Builder setMessageId(long messageId);

        public abstract Builder setEntityId(long entityId);

        public abstract Builder setType(String type);

        public abstract AttachmentDbo build();
    }
}
