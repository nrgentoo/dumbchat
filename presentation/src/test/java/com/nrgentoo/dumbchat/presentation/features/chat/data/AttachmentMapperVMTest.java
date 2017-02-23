package com.nrgentoo.dumbchat.presentation.features.chat.data;

import com.nrgentoo.dumbchat.domain.features.attachments.entity.Attachment;
import com.nrgentoo.dumbchat.domain.features.attachments.entity.ChatPhoto;
import com.nrgentoo.dumbchat.domain.features.attachments.entity.PhotoAttachment;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

/**
 * Test {@link AttachmentMapperVM}
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest(ChatPhotoMapperVM.class)
public class AttachmentMapperVMTest {

    private static final long ATTACHMENT_ID = 10L;
    private static final long MESSAGE_ID = 12L;

    private PhotoAttachment mPhotoAttachment;

    @Mock
    ChatPhoto mockChatPhoto;

    @Mock
    ChatPhotoVM mockChatPhotoVM;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mockStatic(ChatPhotoMapperVM.class);

        when(ChatPhotoMapperVM.transform(mockChatPhoto))
                .thenReturn(mockChatPhotoVM);

        mPhotoAttachment = new PhotoAttachment(mockChatPhoto);
        mPhotoAttachment.setId(ATTACHMENT_ID);
        mPhotoAttachment.setMessageId(MESSAGE_ID);
    }

    @Test
    public void transform() throws Exception {
        AttachmentVM<?> attachmentVM = AttachmentMapperVM.transform(mPhotoAttachment);

        assertThat(attachmentVM.type()).isEqualTo(Attachment.TYPE_PHOTO);
        assertThat(attachmentVM.id()).isEqualTo(ATTACHMENT_ID);
        assertThat(attachmentVM.messageId()).isEqualTo(MESSAGE_ID);
        assertThat(attachmentVM.attachment()).isInstanceOf(ChatPhotoVM.class);
        assertThat(attachmentVM.attachment()).isEqualTo(mockChatPhotoVM);
    }

    @Test
    public void transformCollection() throws Exception {

    }

}