package com.nrgentoo.dumbchat.presentation.features.chat.data;

import com.nrgentoo.dumbchat.domain.features.attachments.entity.Attachment;
import com.nrgentoo.dumbchat.domain.features.messages.entity.Message;
import com.nrgentoo.dumbchat.domain.features.users.entity.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

/**
 * Test {@link MessageMapperVM}
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest({
        UserMapperVM.class,
        AttachmentMapperVM.class})
public class MessageMapperVMTest {

    private static final Long MESSAGE_ID = 10L;
    private static final String MESSAGE_TEXT = "message text";
    private static final long MESSAGE_TIMESTAMP = System.currentTimeMillis();
    private static final DateFormat DATE_FORMAT = SimpleDateFormat
            .getDateInstance(DateFormat.DEFAULT);

    private MessageMapperVM mMessageMapperVM;

    private List<AttachmentVM<?>> mockAttachmentVMList = Arrays.asList(
            mock(AttachmentVM.class),
            mock(AttachmentVM.class),
            mock(AttachmentVM.class));

    private List<Attachment<?>> mockAttachmentList = Arrays.asList(
            mock(Attachment.class),
            mock(Attachment.class),
            mock(Attachment.class));

    @Mock
    UserVM mockMyselfVM;

    @Mock
    User mockMyself;

    private Message mMyMessage;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mockStatic(UserMapperVM.class);
        when(UserMapperVM.transform(isA(User.class)))
                .thenReturn(mock(UserVM.class));
        when(UserMapperVM.transform(mockMyself))
                .thenReturn(mockMyselfVM);

        mockStatic(AttachmentMapperVM.class);
        when(AttachmentMapperVM.transform(mockAttachmentList))
                .thenReturn(mockAttachmentVMList);

        mMyMessage = createMessage(MESSAGE_ID, MESSAGE_TEXT, MESSAGE_TIMESTAMP, mockMyself);
    }

    @After
    public void tearDown() throws Exception {
        mMyMessage = null;
    }

    private Message createMessage(long messageId, String messageText, long timeStamp, User author) {
        return Message.builder()
                .id(messageId)
                .text(messageText)
                .timeStamp(timeStamp)
                .author(author)
                .attachments(mockAttachmentList)
                .build();
    }

    private List<Message> createMessageList(int count) {
        List<Message> messageList = new LinkedList<>();

        for (int i = 0; i < count; i++) {
            Message message = createMessage(i + 1, MESSAGE_TEXT + "_" + i,
                    MESSAGE_TIMESTAMP + i * 1000, mock(User.class));
            messageList.add(message);
        }

        return messageList;
    }

    @Test
    public void transform() throws Exception {
        MessageVM myMessageVM = MessageMapperVM.transform(mMyMessage, mockMyself, DATE_FORMAT);

        assertThat(myMessageVM.id()).isEqualTo(MESSAGE_ID);
        assertThat(myMessageVM.attachments()).containsExactlyElementsOf(mockAttachmentVMList);
        assertThat(myMessageVM.author()).isEqualTo(mockMyselfVM);
        assertThat(myMessageVM.text()).isEqualTo(MESSAGE_TEXT);
        assertThat(myMessageVM.isMine()).isTrue();

        String dateTimeStr = DATE_FORMAT.format(MESSAGE_TIMESTAMP);
        assertThat(myMessageVM.dateTime()).isEqualTo(dateTimeStr);

        User otherUser = mock(User.class);
        UserVM otherUserVM = mock(UserVM.class);
        long id = 12;
        String messageText = "other text";
        long timeStamp = System.currentTimeMillis() + 1000;
        Message otherMessage = createMessage(id, messageText, timeStamp, otherUser);

        when(UserMapperVM.transform(otherUser))
                .thenReturn(otherUserVM);

        MessageVM otherMessageVM = MessageMapperVM.transform(otherMessage, mockMyself, DATE_FORMAT);

        assertThat(otherMessageVM.id()).isEqualTo(id);
        assertThat(otherMessageVM.attachments()).containsExactlyElementsOf(mockAttachmentVMList);
        assertThat(otherMessageVM.author()).isEqualTo(otherUserVM);
        assertThat(otherMessageVM.author()).isNotEqualTo(mockMyselfVM);
        assertThat(otherMessageVM.text()).isEqualTo(messageText);
        assertThat(otherMessageVM.isMine()).isFalse();

        dateTimeStr = DATE_FORMAT.format(timeStamp);
        assertThat(otherMessageVM.dateTime()).isEqualTo(dateTimeStr);
    }

    @Test
    public void transform1() throws Exception {
        List<Message> messages = createMessageList(10);
        List<MessageVM> messageVMList = MessageMapperVM.transform(messages, mockMyself,
                DATE_FORMAT);

        assertThat(messageVMList).hasSize(10);
        for (int i = 0; i < messageVMList.size(); i++) {
            MessageVM messageVM = messageVMList.get(i);
            Message message = messages.get(i);

            assertThat(messageVM.id()).isEqualTo(message.getId());
            assertThat(messageVM.attachments()).containsExactlyElementsOf(mockAttachmentVMList);
            assertThat(messageVM.author()).isNotNull();
            assertThat(messageVM.text()).isEqualTo(message.getText());
            assertThat(messageVM.dateTime())
                    .isEqualTo(DATE_FORMAT.format(new Date(message.getTimeStamp())));
            assertThat(messageVM.isMine()).isFalse();
        }
    }

}