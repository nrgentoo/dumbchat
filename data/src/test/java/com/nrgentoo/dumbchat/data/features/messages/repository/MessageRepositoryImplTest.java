package com.nrgentoo.dumbchat.data.features.messages.repository;

import com.nrgentoo.dumbchat.domain.features.messages.entity.Message;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

/**
 * Test {@link MessageRepositoryImpl}
 */

@RunWith(MockitoJUnitRunner.class)
public class MessageRepositoryImplTest {

    @Mock
    DbMessageRepo mockDbMessageRepo;

    @Mock
    CloudMessageRepo mockCloudMessageRepo;

    private List<Message> mockMessages = Arrays.asList(
            mock(Message.class),
            mock(Message.class),
            mock(Message.class));

    @Mock
    Message mockMessage;

    private MessageRepositoryImpl mMessageRepository;

    @Before
    public void setUp() throws Exception {
        mMessageRepository = new MessageRepositoryImpl();
        mMessageRepository.mDbMessageRepo = mockDbMessageRepo;
        mMessageRepository.mCloudMessageRepo = mockCloudMessageRepo;
    }

    @After
    public void tearDown() throws Exception {
        mMessageRepository = null;
    }

    @Test
    public void get() throws Exception {
        mMessageRepository.get(1);

        verify(mockDbMessageRepo).get(1);
        verifyZeroInteractions(mockCloudMessageRepo);
    }

    @Test
    public void localDbEmpty_callCloudRepo() throws Exception {
        when(mockDbMessageRepo.getAll())
                .thenReturn(Collections.emptyList());
        when(mockCloudMessageRepo.getAll())
                .thenReturn(mockMessages);

        List<Message> messages = mMessageRepository.getAll();

        assertThat(messages).containsExactlyElementsOf(mockMessages);
    }

    @Test
    public void save() throws Throwable {
        mMessageRepository.save(mockMessage);

        verify(mockDbMessageRepo).save(mockMessage);
        verifyZeroInteractions(mockCloudMessageRepo);
    }

    @Test
    public void saveCollection() throws Throwable {
        mMessageRepository.save(mockMessages);

        verify(mockDbMessageRepo).save(mockMessages);
        verifyZeroInteractions(mockCloudMessageRepo);
    }

    @Test
    public void delete() throws Throwable {
        mMessageRepository.delete(mockMessage);

        verify(mockDbMessageRepo).delete(mockMessage);
        verifyZeroInteractions(mockCloudMessageRepo);
    }

}