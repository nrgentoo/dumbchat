package com.nrgentoo.dumbchat.data.features.messages.repository;

import com.nrgentoo.dumbchat.data.features.messages.cloud.ChatApi;
import com.nrgentoo.dumbchat.domain.features.messages.entity.Message;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test {@link CloudMessageRepo}
 */

@RunWith(MockitoJUnitRunner.class)
public class CloudMessageRepoTest {

    @Mock
    ChatApi mockChatApi;

    private List<Message> mockMessages = Arrays.asList(
            mock(Message.class),
            mock(Message.class),
            mock(Message.class));

    private CloudMessageRepo mCloudMessageRepo;

    @Before
    public void setUp() throws Exception {
        mCloudMessageRepo = new CloudMessageRepo();
        mCloudMessageRepo.mChatApi = mockChatApi;

        when(mockChatApi.getMessageHistory())
                .thenReturn(mockMessages);
    }

    @After
    public void tearDown() throws Exception {
        mCloudMessageRepo = null;
    }

    @Test(expected = IllegalStateException.class)
    public void get() throws Exception {
        mCloudMessageRepo.get(1);
    }

    @Test
    public void getAll() throws Exception {
        List<Message> messages = mCloudMessageRepo.getAll();

        assertThat(messages).containsExactlyElementsOf(mockMessages);
    }

    @Test(expected = IllegalStateException.class)
    public void save() throws Throwable {
        mCloudMessageRepo.save(mock(Message.class));
    }

    @Test(expected = IllegalStateException.class)
    public void save1() throws Throwable {
        mCloudMessageRepo.save(mockMessages);
    }

    @Test(expected = IllegalStateException.class)
    public void delete() throws Throwable {
        mCloudMessageRepo.delete(mock(Message.class));
    }

}