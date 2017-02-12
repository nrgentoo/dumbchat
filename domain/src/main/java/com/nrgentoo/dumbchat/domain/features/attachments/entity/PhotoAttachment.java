package com.nrgentoo.dumbchat.domain.features.attachments.entity;

/**
 * Implementation of {@link Attachment} for photos
 */

public class PhotoAttachment extends Attachment<ChatPhoto> {

    public PhotoAttachment(ChatPhoto attachment) {
        super(attachment);
    }

    @Override
    public String type() {
        return TYPE_PHOTO;
    }

    @Override
    public String toString() {
        return get().toString();
    }

    @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
    @Override
    public boolean equals(Object o) {
        return get().equals(o);
    }

    @Override
    public int hashCode() {
        return get().hashCode();
    }
}
