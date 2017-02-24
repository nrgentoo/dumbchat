package com.nrgentoo.dumbchat.presentation.features.chat.ui;

import com.nrgentoo.dumbchat.domain.core.usecase.UseCaseExecutor;
import com.nrgentoo.dumbchat.domain.features.attachments.entity.Attachment;
import com.nrgentoo.dumbchat.domain.features.attachments.entity.ChatPhoto;
import com.nrgentoo.dumbchat.domain.features.attachments.entity.PhotoAttachment;
import com.nrgentoo.dumbchat.domain.features.messages.entity.Message;
import com.nrgentoo.dumbchat.domain.features.messages.usecase.GetMessagesUseCase;
import com.nrgentoo.dumbchat.domain.features.messages.usecase.PostMessageUseCase;
import com.nrgentoo.dumbchat.domain.features.users.entity.User;
import com.nrgentoo.dumbchat.domain.features.users.usecase.GetMyselfUseCase;
import com.nrgentoo.dumbchat.presentation.features.chat.data.AttachmentMapperVM;
import com.nrgentoo.dumbchat.presentation.features.chat.data.MessageMapperVM;
import com.nrgentoo.dumbchat.presentation.features.chat.data.MessageVM;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.text.DateFormat;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Provider;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subscribers.DisposableSubscriber;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

/**
 * Test {@link ChatPresenter}
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest({MessageMapperVM.class, AttachmentMapperVM.class})
public class ChatPresenterTest {

    private static final String MESSAGE_TEXT = "message text";
    private static final long MY_USER_ID = 1;

    @Mock
    GetMessagesUseCase mockGetMessagesUseCase;

    @Mock
    UseCaseExecutor mockUseCaseExecutor;

    @Mock
    Provider<PostMessageUseCase> mockPostMessageUseCaseProvider;

    @Mock
    PostMessageUseCase mockPostMessageUseCase;

    @Mock
    UseCaseExecutor mockPostMessageExecutor;

    @Mock
    GetMyselfUseCase mockGetMyselfUseCase;

    @Mock
    User mockMyselfUser;

    @Mock
    ChatView mockChatView;

    private PublishSubject<List<Message>> mMessagesSubject = PublishSubject.create();

    private ChatPresenter mChatPresenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mChatPresenter = new ChatPresenter();

        when(mockGetMessagesUseCase.execute(null))
                .thenReturn(mMessagesSubject.toFlowable(BackpressureStrategy.BUFFER));

        when(mockPostMessageUseCaseProvider.get())
                .thenReturn(mockPostMessageUseCase);

        when(mockGetMyselfUseCase.execute(null))
                .thenReturn(Single.just(mockMyselfUser));

        when(mockMyselfUser.getId())
                .thenReturn(MY_USER_ID);

        mChatPresenter.mGetMessagesUseCase = mockGetMessagesUseCase;
        mChatPresenter.mGetMessagesExecutor = mockUseCaseExecutor;
        mChatPresenter.mPostMessageUseCaseProvider = mockPostMessageUseCaseProvider;
        mChatPresenter.mPostMessageExecutor = mockPostMessageExecutor;
        mChatPresenter.setGetMyselfUseCase(mockGetMyselfUseCase);

        mockStatic(MessageMapperVM.class);
    }

    @After
    public void tearDown() throws Exception {
        mChatPresenter = null;
    }

    @Test
    public void getAttachView() throws Exception {
        //noinspection unchecked
        doAnswer(invocation -> {
            //noinspection unchecked
            Flowable<List<Message>> messagesFlowable =
                    (Flowable<List<Message>>) invocation.getArguments()[0];

            //noinspection unchecked
            DisposableSubscriber<List<Message>> subscriber =
                    (DisposableSubscriber<List<Message>>) invocation.getArguments()[1];

            messagesFlowable.subscribeWith(subscriber);
            return null;
        }).when(mockUseCaseExecutor).execute(isA(Flowable.class), isA(DisposableSubscriber.class));

        mChatPresenter.attachView(mockChatView);

        List<Message> firstMessages = getMockMessages(5);
        List<MessageVM> firstMessagesVM = getMockMessagesVM(5);
        when(MessageMapperVM.transform(eq(firstMessages), eq(mockMyselfUser),
                isA(DateFormat.class)))
                .thenReturn(firstMessagesVM);

        List<Message> secondMessages = getMockMessages(3);
        List<MessageVM> secondMessageVM = getMockMessagesVM(3);
        when(MessageMapperVM.transform(eq(secondMessages), eq(mockMyselfUser),
                isA(DateFormat.class)))
                .thenReturn(secondMessageVM);

        mMessagesSubject.onNext(firstMessages);
        mMessagesSubject.onNext(secondMessages);

        verify(mockChatView).notifyMessagesInserted(0, 5);
        verify(mockChatView).notifyMessagesInserted(5, 3);

        List<MessageVM> expectedMessages = new LinkedList<>(firstMessagesVM);
        expectedMessages.addAll(secondMessageVM);
        assertThat(mChatPresenter.mMessages).containsExactlyElementsOf(expectedMessages);
    }

    private List<Message> getMockMessages(int count) {
        List<Message> messages = new LinkedList<>();

        for (int i = 0; i < count; i++) {
            messages.add(mock(Message.class));
        }

        return messages;
    }

    private List<MessageVM> getMockMessagesVM(int count) {
        List<MessageVM> messages = new LinkedList<>();

        for (int i = 0; i < count; i++) {
            messages.add(mock(MessageVM.class));
        }

        return messages;
    }

    private void mockPostMessageExecutor() {
        //noinspection unchecked
        doAnswer(invocation -> {
            //noinspection unchecked
            Single<Message> messageSingle =
                    (Single<Message>) invocation.getArguments()[0];

            //noinspection unchecked
            DisposableSingleObserver<Message> subscriber =
                    (DisposableSingleObserver<Message>) invocation.getArguments()[1];

            messageSingle.subscribeWith(subscriber);
            return null;
        }).when(mockPostMessageExecutor).execute(isA(Single.class),
                isA(DisposableSingleObserver.class));
    }

    @Test
    public void postMessage_noAttachments() throws Exception {
        mockPostMessageExecutor();

        PostMessageUseCase.Params expectedParams = PostMessageUseCase.Params.builder()
                .setAuthorId(MY_USER_ID)
                .setMessageText(MESSAGE_TEXT)
                .build();

        mChatPresenter.postMessage(MESSAGE_TEXT);

        verify(mockPostMessageUseCase).execute(eq(expectedParams));
        //noinspection unchecked
        verify(mockPostMessageExecutor).execute(isA(Single.class),
                isA(DisposableSingleObserver.class));
    }

    @Test
    public void postMessage_withPhotos() throws Exception {
        mockPostMessageExecutor();

        String photoPath = "path to photo file";
        ChatPhoto chatPhoto = ChatPhoto.builder()
                .uri(photoPath)
                .build();
        //noinspection unchecked
        Attachment<ChatPhoto> photoAttachment = new PhotoAttachment(chatPhoto);

        List<Attachment<?>> attachments = Collections.singletonList(photoAttachment);
        PostMessageUseCase.Params expectedParams = PostMessageUseCase.Params.builder()
                .setAuthorId(MY_USER_ID)
                .setMessageText(MESSAGE_TEXT)
                .setAttachments(attachments)
                .build();

        mChatPresenter.postMessage(MESSAGE_TEXT, Collections.singletonList(photoPath));

        verify(mockPostMessageUseCase).execute(eq(expectedParams));
        //noinspection unchecked
        verify(mockPostMessageExecutor).execute(isA(Single.class),
                isA(DisposableSingleObserver.class));
    }

    @Test
    public void appendPhoto() throws Exception {
        mChatPresenter.attachView(mockChatView);
        assertThat(mChatPresenter.mPhotoUris).isEmpty();

        String photoUri = "photo uri";
        mChatPresenter.appendPhoto(photoUri);

        assertThat(mChatPresenter.mPhotoUris).hasSize(1);
        assertThat(mChatPresenter.mPhotoUris).contains(photoUri);
        verify(mockChatView).notifyPhotoAppended(0);
    }

    @Test
    public void removePhoto() throws Exception {
        mChatPresenter.attachView(mockChatView);
        String photoUri = "photo uri";
        mChatPresenter.mPhotoUris.add(photoUri);

        mChatPresenter.removePhoto(0);
        assertThat(mChatPresenter.mPhotoUris).doesNotContain(photoUri);
        verify(mockChatView).notifyPhotoRemoved(0);
    }
}