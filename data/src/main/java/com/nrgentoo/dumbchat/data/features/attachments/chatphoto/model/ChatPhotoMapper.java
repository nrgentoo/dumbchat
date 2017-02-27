package com.nrgentoo.dumbchat.data.features.attachments.chatphoto.model;

import com.nrgentoo.dumbchat.domain.features.attachments.entity.ChatPhoto;

/**
 * Chat photo mapper
 */

public final class ChatPhotoMapper {

    private ChatPhotoMapper() {
    }

    /**
     * Map a {@link ChatPhotoDbo} from data layer to a {@link ChatPhoto} from domain layer
     */
    public static ChatPhoto transform(ChatPhotoDbo chatPhotoDbo) {
        return ChatPhoto.builder()
                .id(chatPhotoDbo.id())
                .uri(chatPhotoDbo.uri())
                .build();
    }
}
