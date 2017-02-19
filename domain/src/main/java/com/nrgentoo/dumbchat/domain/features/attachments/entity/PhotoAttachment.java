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
}
