package com.nrgentoo.dumbchat.presentation.features.chat.data;

import com.nrgentoo.dumbchat.domain.features.attachments.entity.Attachment;
import com.nrgentoo.dumbchat.domain.features.attachments.entity.ChatPhoto;
import com.nrgentoo.dumbchat.domain.features.attachments.entity.PhotoAttachment;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
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

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mockStatic(ChatPhotoMapperVM.class);

        when(ChatPhotoMapperVM.transform(isA(ChatPhoto.class)))
                .thenReturn(mock(ChatPhotoVM.class));

        mPhotoAttachment = new PhotoAttachment(mock(ChatPhoto.class));
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
    }

    @Test
    public void transformCollection() throws Exception {
        List<Attachment<?>> attachments = createAttachments(3, MESSAGE_ID);

        List<AttachmentVM<?>> attachmentVMList = AttachmentMapperVM.transform(attachments);

        assertThat(attachmentVMList).hasSize(3);
        for (AttachmentVM<?> attachmentVM : attachmentVMList) {
            assertThat(attachmentVM.type()).isEqualTo(Attachment.TYPE_PHOTO);
            assertThat(attachmentVM.messageId()).isEqualTo(MESSAGE_ID);
            assertThat(attachmentVM.attachment()).isNotNull();
            assertThat(attachmentVM.attachment()).isInstanceOf(ChatPhotoVM.class);
        }
    }

    private List<Attachment<?>> createAttachments(int count, long messageId) {
        List<Attachment<?>> attachments = new LinkedList<>();

        for (int i = 0; i < count; i++) {
            Attachment<?> attachment = new PhotoAttachment(mock(ChatPhoto.class));
            attachment.setId(i + 1);
            attachment.setMessageId(messageId);

            attachments.add(attachment);
        }

        return attachments;
    }
}