package com.nrgentoo.dumbchat.domain.features.messages.usecase;

import com.nrgentoo.dumbchat.domain.core.event.MockEventsPort;
import com.nrgentoo.dumbchat.domain.core.executor.PostExecutionThread;
import com.nrgentoo.dumbchat.domain.core.executor.ThreadExecutor;
import com.nrgentoo.dumbchat.domain.features.messages.entity.Message;
import com.nrgentoo.dumbchat.domain.features.messages.event.NewMessageEvent;
import com.nrgentoo.dumbchat.domain.features.messages.repository.MessageRepo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.subscribers.TestSubscriber;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test {@link GetMessagesUseCase}
 */

@RunWith(MockitoJUnitRunner.class)
public class GetMessagesUseCaseTest {

    private static final Long MOCK_NEW_MESSAGE_ID = 55L;
    private static final int MOCK_GET_ALL_SIZE = 5;

    @Mock
    ThreadExecutor mockThreadExecutor;

    @Mock
    PostExecutionThread mockPostExecutionThread;

    @Mock
    MessageRepo mockMessageRepo;

    @Mock
    NewMessageEvent mockNewMessageEvent;

    @Mock
    Message mockNewMessage;

    private MockEventsPort mockEventsPort;

    private GetMessagesUseCase getMessageUseCase;

    @Before
    public void setUp() throws Exception {
        mockEventsPort = new MockEventsPort();

        getMessageUseCase = new GetMessagesUseCase(mockThreadExecutor, mockPostExecutionThread);
        getMessageUseCase.setMessageRepo(mockMessageRepo);
        getMessageUseCase.setEventsPort(mockEventsPort);

        when(mockMessageRepo.getAll())
                .thenReturn(getMockMessageHistory());
        when(mockMessageRepo.get(MOCK_NEW_MESSAGE_ID))
                .thenReturn(mockNewMessage);

        when(mockNewMessageEvent.messageId())
                .thenReturn(MOCK_NEW_MESSAGE_ID);
    }

    @After
    public void tearDown() throws Exception {
        getMessageUseCase = null;
        mockEventsPort = null;
    }

    @Test
    public void execute() throws Exception {
        TestSubscriber<List<Message>> subscriber = new TestSubscriber<>();

        getMessageUseCase.execute(null).subscribeWith(subscriber);
        mockEventsPort.broadcast(NewMessageEvent.class, mockNewMessageEvent);
        subscriber.dispose();

        subscriber.assertNoErrors();
        subscriber.assertValueCount(2);

        List<List<Message>> values = subscriber.values();

        List<Message> firstValue = values.get(0);
        assertThat(firstValue).hasSize(MOCK_GET_ALL_SIZE);

        List<Message> secondValue = values.get(1);
        assertThat(secondValue).hasSize(1);
        Message message = secondValue.get(0);
        assertThat(message).isSameAs(mockNewMessage);

        // check if eventsPort is empty
        assertThat(mockEventsPort.getCallbacksList(NewMessageEvent.class))
                .isNullOrEmpty();
    }

    private List<Message> getMockMessageHistory() {
        List<Message> messages = new ArrayList<>();

        for (int i = 0; i < MOCK_GET_ALL_SIZE; i++) {
            messages.add(mock(Message.class));
        }

        return messages;
    }

}