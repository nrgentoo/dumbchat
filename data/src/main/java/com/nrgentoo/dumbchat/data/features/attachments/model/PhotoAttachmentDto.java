package com.nrgentoo.dumbchat.data.features.attachments.model;

/**
 * Subclass of {@link AttachmentDto} for photo attachments
 */

public class PhotoAttachmentDto extends AttachmentDto<ChatPhotoDto> {

    public PhotoAttachmentDto(ChatPhotoDto attachment) {
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
