package com.nrgentoo.dumbchat.presentation.features.chat.data;

import com.nrgentoo.dumbchat.domain.features.attachments.entity.Attachment;
import com.nrgentoo.dumbchat.domain.features.attachments.entity.ChatPhoto;

import java.util.ArrayList;
import java.util.List;

/**
 * Map attachment from domain to presentation
 */

public final class AttachmentMapperVM {

    private AttachmentMapperVM() {
    }

    /**
     * Map a {@link Attachment} from domain layer to a {@link AttachmentVM} from presentation layer
     *
     * @param attachment domain object to be transformed
     */
    public static AttachmentVM<?> transform(Attachment<?> attachment) {
        AttachmentVM.Builder<?> builder = AttachmentVM.builder()
                .setId(attachment.getId())
                .setMessageId(attachment.getMessageId())
                .setType(attachment.type());

        return mapAttachment(attachment, builder);
    }

    private static <T> AttachmentVM<T> mapAttachment(Attachment<?> attachment,
                                                     AttachmentVM.Builder<T> builder) {
        switch (attachment.type()) {
            case Attachment.TYPE_PHOTO:
                ChatPhotoVM chatPhoto = ChatPhotoMapperVM.transform((ChatPhoto) attachment.get());
                //noinspection unchecked
                builder.setAttachment((T) chatPhoto);

                return builder.build();
        }

        throw new IllegalArgumentException("Unknown attachment type: " + attachment.type());
    }

    /**
     * Map a list of {@link Attachment} from domain layer to a list of {@link AttachmentVM}
     * from presentation layer
     *
     * @param attachments list of attachments from domain layer to map
     */
    public static List<AttachmentVM<?>> transform(List<Attachment<?>> attachments) {
        List<AttachmentVM<?>> attachmentVMs = new ArrayList<>();

        for (Attachment<?> attachment : attachments) {
            attachmentVMs.add(transform(attachment));
        }

        return attachmentVMs;
    }
}
