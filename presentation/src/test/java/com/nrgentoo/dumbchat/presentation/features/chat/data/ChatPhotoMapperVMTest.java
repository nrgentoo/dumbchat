package com.nrgentoo.dumbchat.presentation.features.chat.data;

import com.nrgentoo.dumbchat.domain.features.attachments.entity.ChatPhoto;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test {@link ChatPhotoMapperVM}
 */

public class ChatPhotoMapperVMTest {

    private static final Long CHAT_PHOTO_ID = 10L;
    private static final String CHAT_PHOTO_URI = "chat photo uri";

    private ChatPhoto mChatPhoto;

    @Before
    public void setUp() throws Exception {
        mChatPhoto = ChatPhoto.builder()
                .id(CHAT_PHOTO_ID)
                .uri(CHAT_PHOTO_URI)
                .build();
    }

    @Test
    public void transform() throws Exception {
        ChatPhotoVM chatPhotoVM = ChatPhotoMapperVM.transform(mChatPhoto);

        assertThat(chatPhotoVM.id()).isEqualTo(CHAT_PHOTO_ID);
        assertThat(chatPhotoVM.uri()).isEqualTo(CHAT_PHOTO_URI);
    }

}