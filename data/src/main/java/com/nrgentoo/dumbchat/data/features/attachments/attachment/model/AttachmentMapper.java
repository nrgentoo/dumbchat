package com.nrgentoo.dumbchat.data.features.attachments.attachment.model;

import com.nrgentoo.dumbchat.domain.features.attachments.entity.Attachment;
import com.nrgentoo.dumbchat.domain.features.attachments.entity.AttachmentEntity;
import com.nrgentoo.dumbchat.domain.features.attachments.entity.ChatPhoto;
import com.nrgentoo.dumbchat.domain.features.attachments.entity.PhotoAttachment;

/**
 * Attachment mapper
 */

public final class AttachmentMapper {

    private AttachmentMapper() {
    }

    /**
     * Map a {@link AttachmentDbo} from data layer to a {@link Attachment} from domain layer
     *
     * @param attachmentDbo attachment database object
     * @param attachmentEntity attached entity
     */
    public static Attachment<?> transform(AttachmentDbo attachmentDbo,
                                          AttachmentEntity attachmentEntity) {
        switch (attachmentDbo.type()) {
            case Attachment.TYPE_PHOTO:
                PhotoAttachment attachment = new PhotoAttachment((ChatPhoto) attachmentEntity);
                attachment.setId(attachmentDbo.id());
                attachment.setMessageId(attachmentDbo.messageId());
                return attachment;
        }

        throw new IllegalArgumentException("Unknown attachment type: " + attachmentDbo.type());
    }
}
