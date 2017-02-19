package com.nrgentoo.dumbchat.data.features.attachments.attachment.repository;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.nrgentoo.dumbchat.data.core.db.DbContext;
import com.nrgentoo.dumbchat.data.core.db.DbOpenHelper;
import com.nrgentoo.dumbchat.data.features.attachments.chatphoto.repository.DbChatPhotoRepo;
import com.nrgentoo.dumbchat.domain.features.attachments.entity.Attachment;
import com.nrgentoo.dumbchat.domain.features.attachments.entity.AttachmentEntity;
import com.nrgentoo.dumbchat.domain.features.attachments.entity.ChatPhoto;
import com.nrgentoo.dumbchat.domain.features.attachments.entity.PhotoAttachment;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test {@link DbAttachmentRepo}
 */

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class DbAttachmentRepoTest {

    private static final long ATTACHMENT_ID = 1;
    private static final long MESSAGE_ID = 100;
    private static final long ENTITY_ID = 20;
    private static final String ATTACHMENT_TYPE = Attachment.TYPE_PHOTO;

    @Mock
    DbContext mockDbContext;

    @Mock
    DbChatPhotoRepo mockDbChatPhotoRepo;

    @Mock
    ChatPhoto mockChatPhoto;

    private DbAttachmentRepo mDbAttachmentRepo;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        DbOpenHelper dbOpenHelper = new DbOpenHelper(RuntimeEnvironment.application);
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();

        when(mockDbContext.getDatabase())
                .thenReturn(db);

        mDbAttachmentRepo = new DbAttachmentRepo();
        mDbAttachmentRepo.mDbContext = mockDbContext;
        mDbAttachmentRepo.mDbChatPhotoRepo = mockDbChatPhotoRepo;

        when(mockDbChatPhotoRepo.get(ENTITY_ID)).thenReturn(mockChatPhoto);

        when(mockChatPhoto.getId()).thenReturn(ENTITY_ID);

        clearDb();
    }

    @After
    public void tearDown() throws Exception {
        clearDb();
    }

    private void clearDb() {
        mockDbContext.getDatabase().delete(AttachmentTable.TABLE_NAME, null, null);
    }

    @Test
    public void get() throws Exception {
        populateTable(1);

        Attachment<?> attachment = mDbAttachmentRepo.get(ATTACHMENT_ID);

        assertThat(attachment.getId()).isEqualTo(ATTACHMENT_ID);
        assertThat(attachment.getMessageId()).isEqualTo(MESSAGE_ID);
        assertThat(attachment.type()).isEqualTo(ATTACHMENT_TYPE);
        assertThat(attachment.get()).isInstanceOf(ChatPhoto.class);
        assertThat(attachment.get()).isEqualTo(mockChatPhoto);
    }

    private void populateTable(int count) {
        for (int i = 0; i < count; i++) {
            ContentValues values = new ContentValues();
            values.put(AttachmentTable.COLUMN_ID, ATTACHMENT_ID + i);

            values.put(AttachmentTable.COLUMN_MESSAGE_ID, MESSAGE_ID);
            values.put(AttachmentTable.COLUMN_ENTITY_ID, ENTITY_ID);
            values.put(AttachmentTable.COLUMN_TYPE, ATTACHMENT_TYPE);

            mockDbContext.getDatabase().insert(AttachmentTable.TABLE_NAME, null, values);
        }
    }

    @Test
    public void getAll() throws Exception {
        populateTable(3);

        List<Attachment<?>> attachments = mDbAttachmentRepo.getAll();

        assertThat(attachments).hasSize(3);
        for (Attachment<?> attachment : attachments) {
            assertThat(attachment.get()).isNotNull();
            assertThat(attachment.get()).isInstanceOf(ChatPhoto.class);
        }
    }

    @Test
    public void getForMessage() throws Exception {
        populateTable(3);

        List<Attachment<?>> attachments = mDbAttachmentRepo.getForMessage(111);
        assertThat(attachments).isEmpty();

        attachments = mDbAttachmentRepo.getForMessage(MESSAGE_ID);
        assertThat(attachments).hasSize(3);
        for (Attachment<?> attachment : attachments) {
            assertThat(attachment.get()).isNotNull();
            assertThat(attachment.get()).isInstanceOf(ChatPhoto.class);
        }
    }

    @Test
    public void save() throws Throwable {
        Attachment<?> attachment = new PhotoAttachment(mockChatPhoto);
        attachment.setMessageId(MESSAGE_ID);
        assertThat(attachment.getId()).isNull();

        mDbAttachmentRepo.save(attachment);
        assertThat(attachment.getId()).isNotNull();
        verify(mockDbChatPhotoRepo).save(mockChatPhoto);

        Attachment<?> savedAttachment = mDbAttachmentRepo.get(attachment.getId());

        assertThat(savedAttachment).isEqualTo(attachment);
    }

    @Test
    public void saveCollection() throws Throwable {
        List<Attachment<?>> attachments = new LinkedList<>();
        for (int i = 0; i < 3; i++) {
            Attachment<?> attachment = new PhotoAttachment(mockChatPhoto);
            attachment.setMessageId(MESSAGE_ID);
            attachments.add(attachment);
        }

        mDbAttachmentRepo.save(attachments);

        List<Attachment<?>> savedAttachments = mDbAttachmentRepo.getAll();
        assertThat(savedAttachments).hasSize(3);

        for (Attachment<? extends AttachmentEntity> attachment : attachments) {
            assertThat(attachment.getId()).isNotNull();
            assertThat(attachment.get()).isNotNull();
            assertThat(attachment.get()).isEqualTo(mockChatPhoto);
        }
    }

    @Test
    public void delete() throws Exception {
        populateTable(1);

        Attachment<? extends AttachmentEntity> attachment = mDbAttachmentRepo.get(ATTACHMENT_ID);
        assertThat(attachment).isNotNull();

        mDbAttachmentRepo.delete(attachment);
        attachment = mDbAttachmentRepo.get(ATTACHMENT_ID);
        assertThat(attachment).isNull();
        verify(mockDbChatPhotoRepo).delete(mockChatPhoto);
    }

}