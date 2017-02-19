package com.nrgentoo.dumbchat.domain.features.attachments.entity;

import javax.annotation.Nullable;

/**
 * Attachment object
 */

public abstract class Attachment<T extends AttachmentEntity> {

    public static final String TYPE_PHOTO = "photo";

    @Nullable
    private Long mId;

    @Nullable
    private Long mMessageId;

    private final T mAttachment;

    public Attachment(T attachment) {
        this.mAttachment = attachment;
    }

    public abstract String type();

    public T get() {
        return mAttachment;
    }

    @Nullable
    public Long getId() {
        return mId;
    }

    public void setId(long id) {
        this.mId = id;
    }

    @Nullable
    public Long getMessageId() {
        return mMessageId;
    }

    public void setMessageId(long messageId) {
        this.mMessageId = messageId;
    }

    @Override
    public String toString() {
        return get().toString();
    }

    @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
    @Override
    public boolean equals(Object o) {
        return o instanceof Attachment && get().equals(((Attachment) o).get());
    }

    @Override
    public int hashCode() {
        return get().hashCode();
    }
}
