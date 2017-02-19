package com.nrgentoo.dumbchat.data.features.messages.repository;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.nrgentoo.dumbchat.data.core.db.DbContext;
import com.nrgentoo.dumbchat.data.core.db.DbOpenHelper;
import com.nrgentoo.dumbchat.domain.features.attachments.entity.Attachment;
import com.nrgentoo.dumbchat.domain.features.attachments.repository.AttachmentRepo;
import com.nrgentoo.dumbchat.domain.features.messages.entity.Message;
import com.nrgentoo.dumbchat.domain.features.users.entity.User;
import com.nrgentoo.dumbchat.domain.features.users.repository.UserRepo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test {@link DbMessageRepo}
 */

@RunWith(RobolectricTestRunner.class)
@Config(manifest= Config.NONE)
public class DbMessageRepoTest {

    private static final long MESSAGE_ID = 1;
    private static final String MESSAGE_TEXT = "mock message";
    private static final long MESSAGE_TIMESTAMP = System.currentTimeMillis();
    private static final long AUTHOR_ID = 10;

    @Mock
    DbContext mockDbContext;

    @Mock
    UserRepo mockUserRepo;

    @Mock
    AttachmentRepo mockAttachmentRepo;

    @Mock
    User mockUser;

    private List<Attachment<?>> mockAttachments = Arrays.asList(
            mock(Attachment.class),
            mock(Attachment.class),
            mock(Attachment.class)
    );

    private DbMessageRepo mDbMessageRepo;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        DbOpenHelper dbOpenHelper = new DbOpenHelper(RuntimeEnvironment.application);
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();

        when(mockDbContext.getDatabase()).thenReturn(db);

        mDbMessageRepo = new DbMessageRepo();
        mDbMessageRepo.mDbContext = mockDbContext;
        mDbMessageRepo.mUserRepo = mockUserRepo;
        mDbMessageRepo.mAttachmentRepo = mockAttachmentRepo;

        when(mockUserRepo.get(AUTHOR_ID)).thenReturn(mockUser);
        when(mockAttachmentRepo.getForMessage(anyLong())).thenReturn(mockAttachments);

        when(mockUser.getId()).thenReturn(AUTHOR_ID);

        clearDb();
    }

    @After
    public void tearDown() throws Exception {
        clearDb();
    }

    private void clearDb() {
        mockDbContext.getDatabase().delete(MessageTable.TABLE_NAME, null, null);
    }

    @Test
    public void get() throws Exception {
        populateTable(1);

        Message message = mDbMessageRepo.get(MESSAGE_ID);

        assertThat(message.getId()).isEqualTo(MESSAGE_ID);
        assertThat(message.getText()).isEqualTo(MESSAGE_TEXT);
        assertThat(message.getTimeStamp()).isEqualTo(MESSAGE_TIMESTAMP);
        assertThat(message.getAuthor()).isEqualTo(mockUser);
        assertThat(message.getAttachments()).containsAll(mockAttachments);
    }

    private void populateTable(int count) {
        for (int i = 0; i < count; i++) {
            ContentValues values = new ContentValues();
            values.put(MessageTable.COLUMN_ID, MESSAGE_ID + i);
            values.put(MessageTable.COLUMN_TEXT, MESSAGE_TEXT);
            values.put(MessageTable.COLUMN_TIMESTAMP, MESSAGE_TIMESTAMP + i*1000);
            values.put(MessageTable.COLUMN_AUTHOR_ID, AUTHOR_ID);

            mockDbContext.getDatabase().insert(MessageTable.TABLE_NAME, null, values);
        }
    }

    @Test
    public void getAll() throws Exception {
        populateTable(3);

        List<Message> messages = mDbMessageRepo.getAll();

        assertThat(messages).hasSize(3);
        for (Message message : messages) {
            assertThat(message.getAuthor()).isNotNull();
            assertThat(message.getAttachments()).containsAll(mockAttachments);
        }
    }

    @Test
    public void save() throws Throwable {
        Message message = Message.builder()
                .text(MESSAGE_TEXT)
                .author(mockUser)
                .timeStamp(MESSAGE_TIMESTAMP)
                .attachments(mockAttachments)
                .build();

        mDbMessageRepo.save(message);

        assertThat(message.getId()).isNotNull();

        Message savedMessage = mDbMessageRepo.get(message.getId());
        assertThat(savedMessage.getId()).isEqualTo(message.getId());
        assertThat(savedMessage.getAuthor()).isEqualTo(mockUser);
        assertThat(savedMessage.getTimeStamp()).isEqualTo(MESSAGE_TIMESTAMP);

        for (Attachment<?> attachment : mockAttachments) {
            verify(attachment).setMessageId(message.getId());
        }

        verify(mockAttachmentRepo).save(mockAttachments);
    }

    @Test
    public void saveCollection() throws Throwable {
        List<Message> messages = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Message message = Message.builder()
                    .text(MESSAGE_TEXT + "_" + i)
                    .author(mockUser)
                    .timeStamp(MESSAGE_TIMESTAMP + i * 1000)
                    .attachments(mockAttachments)
                    .build();
            messages.add(message);
        }

        mDbMessageRepo.save(messages);

        List<Message> savedMessages = mDbMessageRepo.getAll();
        assertThat(savedMessages).hasSize(3);

        for (Message message : savedMessages) {
            assertThat(message.getId()).isNotNull();
            assertThat(message.getAuthor()).isEqualTo(mockUser);
            assertThat(message.getAttachments()).isNotNull();
            assertThat(message.getAttachments()).containsAll(mockAttachments);
        }
    }

    @Test
    public void delete() throws Throwable {
        populateTable(1);

        Message message = mDbMessageRepo.get(MESSAGE_ID);
        assertThat(message).isNotNull();

        mDbMessageRepo.delete(message);
        message = mDbMessageRepo.get(MESSAGE_ID);
        assertThat(message).isNull();
        for (Attachment<?> attachment : mockAttachments) {
            verify(mockAttachmentRepo).delete(attachment);
        }
    }

}