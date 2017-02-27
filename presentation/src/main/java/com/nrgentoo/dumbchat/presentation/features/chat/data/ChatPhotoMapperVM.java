package com.nrgentoo.dumbchat.presentation.features.chat.data;

import com.nrgentoo.dumbchat.domain.features.attachments.entity.ChatPhoto;

/**
 * Map chat photo from domain to presentation
 */

public final class ChatPhotoMapperVM {

    private ChatPhotoMapperVM() {
    }

    /**
     * Map a {@link ChatPhoto} from domain layer to a {@link ChatPhotoVM} from presentation layer
     *
     * @param chatPhoto domain object to be transformed
     */
    public static ChatPhotoVM transform(ChatPhoto chatPhoto) {
        return ChatPhotoVM.builder()
                .setId(chatPhoto.getId())
                .setUri(chatPhoto.getUri())
                .build();
    }
}
