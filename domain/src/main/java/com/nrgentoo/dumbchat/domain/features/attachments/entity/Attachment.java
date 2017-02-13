package com.nrgentoo.dumbchat.domain.features.attachments.entity;

/**
 * Attachment object
 */

public abstract class Attachment<T> {

    public static final String TYPE_PHOTO = "photo";

    private final T mAttachment;

    public Attachment(T attachment) {
        this.mAttachment = attachment;
    }

    public abstract String type();

    public T get() {
        return mAttachment;
    }
}
