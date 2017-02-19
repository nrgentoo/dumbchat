package com.nrgentoo.dumbchat.domain.features.chat;

import com.nrgentoo.dumbchat.domain.core.event.EventsPort;
import com.nrgentoo.dumbchat.domain.core.repository.UnitOfWork;
import com.nrgentoo.dumbchat.domain.features.attachments.entity.Attachment;
import com.nrgentoo.dumbchat.domain.features.bot.BotService;
import com.nrgentoo.dumbchat.domain.features.messages.entity.Message;
import com.nrgentoo.dumbchat.domain.features.messages.event.NewMessageEvent;
import com.nrgentoo.dumbchat.domain.features.notification.NotificationService;
import com.nrgentoo.dumbchat.domain.features.users.entity.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

/**
 * Test {@link OfflineChatService}
 */

@RunWith(MockitoJUnitRunner.class)
public class OfflineChatServiceTest {

    private static final int MESSAGE_COUNT = 10;
    private static final int ATTACHMENTS_COUNT = 2;

    @Mock
    BotService mockBotService;

    @Mock
    EventsPort mockEventsPort;

    @Mock
    UnitOfWork mockUnitOfWork;

    @Mock
    User mockBotUser;

    @Mock
    NotificationService mockNotificationService;

    @Mock
    AppVisibilityService mockAppVisibilityService;

    private OfflineChatService mOfflineChatService;

    @Before
    public void setUp() throws Exception {
        mOfflineChatService = new OfflineChatService();
        mOfflineChatService.mBotService = mockBotService;
        mOfflineChatService.mEventsPort = mockEventsPort;
        mOfflineChatService.mUnitOfWork = mockUnitOfWork;
        mOfflineChatService.mNotificationService = mockNotificationService;
        mOfflineChatService.mAppVisibilityService = mockAppVisibilityService;

        doReturn(getMessages())
                .when(mockBotService).messages();
    }

    @After
    public void tearDown() throws Exception {
        mOfflineChatService = null;
    }

    @Test
    public void connect() throws Throwable {
        mOfflineChatService.connect();

        verify(mockUnitOfWork, times(MESSAGE_COUNT))
                .insert(isA(Message.class));

        verify(mockUnitOfWork, times(MESSAGE_COUNT)).commit();

        verify(mockEventsPort, times(MESSAGE_COUNT)).broadcast(eq(NewMessageEvent.class),
                any(NewMessageEvent.class));
    }

    @Test
    public void dontShowNotificationWhenAppIsForeground() throws Exception {
        when(mockAppVisibilityService.isInBackground())
                .thenReturn(false);

        mOfflineChatService.connect();

        verifyZeroInteractions(mockNotificationService);
    }

    @Test
    public void showNotificationWhenAppIsInBackground() throws Exception {
        when(mockAppVisibilityService.isInBackground())
                .thenReturn(true);

        mOfflineChatService.connect();

        verify(mockNotificationService, times(MESSAGE_COUNT))
                .notifyNewMessage(isA(Message.class));
    }

    private Flowable<Message> getMessages() {
        List<Message> messages = new ArrayList<>();

        for (int i = 0; i < MESSAGE_COUNT; i++) {
            messages.add(makeMessage());
        }

        return Flowable.fromIterable(messages);
    }

    private Message makeMessage() {
        Message message = mock(Message.class);

        when(message.getAuthor())
                .thenReturn(mockBotUser);

        List<Attachment<?>> attachments = new ArrayList<>();
        for (int i = 0; i < ATTACHMENTS_COUNT; i++) {
            attachments.add(mock(Attachment.class));
        }

        when(message.getAttachments())
                .thenReturn(attachments);

        return message;
    }
}