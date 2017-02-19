package com.nrgentoo.dumbchat.data.features.attachments.attachment.model;

/**
 * Attachment data transfer object
 */

public abstract class AttachmentDto<T> {

    public static final String TYPE_PHOTO = "photo";

    private final T mAttachment;

    public AttachmentDto(T attachment) {
        this.mAttachment = attachment;
    }

    public abstract String type();

    public T get() {
        return mAttachment;
    }
}
