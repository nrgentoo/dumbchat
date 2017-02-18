package com.nrgentoo.dumbchat.domain.features.messages.usecase;

import com.nrgentoo.dumbchat.domain.core.event.EventsPort;
import com.nrgentoo.dumbchat.domain.core.executor.PostExecutionThread;
import com.nrgentoo.dumbchat.domain.core.executor.ThreadExecutor;
import com.nrgentoo.dumbchat.domain.core.repository.UnitOfWork;
import com.nrgentoo.dumbchat.domain.features.attachments.entity.Attachment;
import com.nrgentoo.dumbchat.domain.features.attachments.entity.ChatPhoto;
import com.nrgentoo.dumbchat.domain.features.attachments.entity.PhotoAttachment;
import com.nrgentoo.dumbchat.domain.features.attachments.repository.AttachmentRepo;
import com.nrgentoo.dumbchat.domain.features.messages.entity.Message;
import com.nrgentoo.dumbchat.domain.features.messages.event.NewMessageEvent;
import com.nrgentoo.dumbchat.domain.features.messages.repository.MessageRepo;
import com.nrgentoo.dumbchat.domain.features.users.entity.User;
import com.nrgentoo.dumbchat.domain.features.users.repository.UserRepo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.observers.TestObserver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test {@link PostMessageUseCase}
 */

@RunWith(MockitoJUnitRunner.class)
public class PostMessageUseCaseTest {

    private static final long AUTHOR_ID = 5;
    private static final String MESSAGE_TEXT = "test message";
    private static final String PHOTO_URI = "photo uri";
    private static final Long MESSAGE_ID = 100L;

    @Mock
    ThreadExecutor mockThreadExecutor;

    @Mock
    PostExecutionThread mockPostExecutionThread;

    @Mock
    EventsPort mockEventsPort;

    @Mock
    UnitOfWork mockUnitOfWork;

    @Mock
    MessageRepo mockMessageRepo;

    @Mock
    AttachmentRepo mockAttachmentRepo;

    @Mock
    UserRepo mockUserRepo;

    @Captor
    ArgumentCaptor<NewMessageEvent> mNewMessageEventArgumentCaptor;

    @Mock
    User mockUser;

    private PostMessageUseCase mPostMessageUseCase;

    @Before
    public void setUp() throws Throwable {
        mPostMessageUseCase = new PostMessageUseCase(mockThreadExecutor, mockPostExecutionThread);
        mPostMessageUseCase.mUnitOfWork = mockUnitOfWork;
        mPostMessageUseCase.mEventsPort = mockEventsPort;

        when(mockUnitOfWork.getMessageRepository())
                .thenReturn(mockMessageRepo);

        when(mockUnitOfWork.getAttachmentRepository())
                .thenReturn(mockAttachmentRepo);

        when(mockUnitOfWork.getUserRepository())
                .thenReturn(mockUserRepo);

        when(mockUserRepo.get(AUTHOR_ID))
                .thenReturn(mockUser);

        doAnswer(invocation -> {
            Message message = (Message) invocation.getArguments()[0];
            message.setId(MESSAGE_ID);

            return null;
        }).when(mockUnitOfWork).insert(isA(Message.class));
    }

    @After
    public void tearDown() throws Exception {
        mPostMessageUseCase = null;
    }

    @Test
    public void execute() throws Throwable {
        TestObserver<Message> observer = new TestObserver<>();

        PostMessageUseCase.Params params = createParams();
        mPostMessageUseCase.execute(params).subscribeWith(observer);

        observer.assertNoErrors();
        observer.assertComplete();
        observer.assertValueCount(1);

        Message message = observer.values().get(0);

        assertThat(message.getText()).isEqualTo(MESSAGE_TEXT);
        assertThat(message.getAuthor()).isEqualTo(mockUser);
        assertThat(message.getId()).isEqualTo(MESSAGE_ID);

        verify(mockUnitOfWork).commit();
        verify(mockEventsPort).broadcast(eq(NewMessageEvent.class),
                mNewMessageEventArgumentCaptor.capture());

        assertThat(mNewMessageEventArgumentCaptor.getValue().messageId())
                .isEqualTo(MESSAGE_ID);
    }

    private PostMessageUseCase.Params createParams() {
        return PostMessageUseCase.Params.builder()
                .setAuthorId(AUTHOR_ID)
                .setMessageText(MESSAGE_TEXT)
                .setAttachments(createAttachments())
                .build();
    }

    private List<Attachment<?>> createAttachments() {
        List<Attachment<?>> attachments = new ArrayList<>();

        ChatPhoto chatPhoto = ChatPhoto.builder()
                .uri(PHOTO_URI)
                .build();
        attachments.add(new PhotoAttachment(chatPhoto));

        return attachments;
    }
}