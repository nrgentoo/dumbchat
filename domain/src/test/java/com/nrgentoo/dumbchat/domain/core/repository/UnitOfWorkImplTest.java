package com.nrgentoo.dumbchat.domain.core.repository;

import com.nrgentoo.dumbchat.domain.features.attachments.entity.PhotoAttachment;
import com.nrgentoo.dumbchat.domain.features.attachments.repository.AttachmentRepo;
import com.nrgentoo.dumbchat.domain.features.messages.entity.Message;
import com.nrgentoo.dumbchat.domain.features.messages.repository.MessageRepo;
import com.nrgentoo.dumbchat.domain.features.users.entity.User;
import com.nrgentoo.dumbchat.domain.features.users.repository.UserRepo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Test {@link UnitOfWorkImpl}
 */

@RunWith(MockitoJUnitRunner.class)
public class UnitOfWorkImplTest {

    @Mock
    MessageRepo mockMessageRepo;

    @Mock
    UserRepo mockUserRepo;

    @Mock
    AttachmentRepo mockAttachmentRepo;

    @Mock
    DbContext mockDbContext;

    private UnitOfWorkImpl mUnitOfWork;

    @Before
    public void setUp() throws Exception {
        mUnitOfWork = new UnitOfWorkImpl();

        mUnitOfWork.mDbContext = mockDbContext;
        mUnitOfWork.mMessageRepo = mockMessageRepo;
        mUnitOfWork.mUserRepo = mockUserRepo;
        mUnitOfWork.mAttachmentRepo = mockAttachmentRepo;
        mUnitOfWork.mChangesStorage = new ChangesStorage();
    }

    @After
    public void tearDown() throws Exception {
        mUnitOfWork = null;
    }

    @Test
    public void insert() throws Throwable {
        Message message = mock(Message.class);
        User user = mock(User.class);
        PhotoAttachment attachment = mock(PhotoAttachment.class);

        mUnitOfWork.insert(message);
        mUnitOfWork.insert(user);
        mUnitOfWork.insert(attachment);
        mUnitOfWork.commit();

        verify(mockDbContext).startTransaction();
        verify(mockMessageRepo).save(message);
        verify(mockUserRepo).save(user);
        verify(mockAttachmentRepo).save(attachment);
        verify(mockDbContext).markTransactionSuccessful();
        verify(mockDbContext).endTransaction();
    }

    @Test
    public void insertCollection() throws Throwable {
        List<Message> messages = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            messages.add(mock(Message.class));
        }

        mUnitOfWork.insert(messages);
        mUnitOfWork.commit();

        verify(mockDbContext).startTransaction();
        verify(mockMessageRepo).save(messages);
        verify(mockDbContext).endTransaction();
    }

    @Test
    public void update() throws Throwable {
        Message message = mock(Message.class);
        User user = mock(User.class);
        PhotoAttachment attachment = mock(PhotoAttachment.class);

        mUnitOfWork.update(message);
        mUnitOfWork.update(user);
        mUnitOfWork.update(attachment);
        mUnitOfWork.commit();

        verify(mockDbContext).startTransaction();
        verify(mockMessageRepo).save(message);
        verify(mockUserRepo).save(user);
        verify(mockAttachmentRepo).save(attachment);
        verify(mockDbContext).markTransactionSuccessful();
        verify(mockDbContext).endTransaction();
    }

    @Test
    public void delete() throws Throwable {
        Message message = mock(Message.class);
        User user = mock(User.class);
        PhotoAttachment attachment = mock(PhotoAttachment.class);

        mUnitOfWork.delete(message);
        mUnitOfWork.delete(user);
        mUnitOfWork.delete(attachment);
        mUnitOfWork.commit();

        verify(mockDbContext).startTransaction();
        verify(mockMessageRepo).delete(message);
        verify(mockUserRepo).delete(user);
        verify(mockAttachmentRepo).delete(attachment);
        verify(mockDbContext).markTransactionSuccessful();
        verify(mockDbContext).endTransaction();
    }

    @Test(expected = Exception.class)
    public void testError() throws Throwable {
        Message message = mock(Message.class);

        doThrow(new Exception())
                .when(mockMessageRepo).save(message);

        mUnitOfWork.insert(message);
        mUnitOfWork.commit();

        verify(mockDbContext).startTransaction();
        verify(mockMessageRepo).save(message);
        verify(mockDbContext, times(0)).markTransactionSuccessful();
        verify(mockDbContext).endTransaction();
    }
}