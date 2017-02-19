package com.nrgentoo.dumbchat.data.features.attachments.chatphoto.repository;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.nrgentoo.dumbchat.data.core.db.DbContext;
import com.nrgentoo.dumbchat.data.core.db.DbOpenHelper;
import com.nrgentoo.dumbchat.domain.features.attachments.entity.ChatPhoto;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * Test {@link DbChatPhotoRepo}
 */

@RunWith(RobolectricTestRunner.class)
@Config(manifest= Config.NONE)
public class DbChatPhotoRepoTest {

    private static final long PHOTO_ID = 1;
    private static final String PHOTO_URI = "photo uri";

    @Mock
    DbContext mockDbContext;

    private DbChatPhotoRepo mDbChatPhotoRepo;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        DbOpenHelper dbOpenHelper = new DbOpenHelper(RuntimeEnvironment.application);
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();

        when(mockDbContext.getDatabase())
                .thenReturn(db);

        mDbChatPhotoRepo = new DbChatPhotoRepo();
        mDbChatPhotoRepo.mDbContext = mockDbContext;

        clearDb();
    }

    @After
    public void tearDown() throws Exception {
        clearDb();
    }

    private void clearDb() {
        mockDbContext.getDatabase().delete(ChatPhotoTable.TABLE_NAME, null, null);
    }

    @Test
    public void get() throws Exception {
        populateTable(1);

        ChatPhoto chatPhoto = mDbChatPhotoRepo.get(PHOTO_ID);

        assertThat(chatPhoto.getId()).isEqualTo(PHOTO_ID);
        assertThat(chatPhoto.getUri()).isEqualTo(PHOTO_URI);
    }

    private void populateTable(int count) {
        for (int i = 0; i < count; i++) {
            ContentValues values = new ContentValues();
            values.put(ChatPhotoTable.COLUMN_ID, PHOTO_ID + i);
            values.put(ChatPhotoTable.COLUMN_URI, PHOTO_URI);

            mockDbContext.getDatabase().insert(ChatPhotoTable.TABLE_NAME, null, values);
        }
    }

    @Test
    public void getAll() throws Exception {
        populateTable(3);

        List<ChatPhoto> chatPhotos = mDbChatPhotoRepo.getAll();

        assertThat(chatPhotos).hasSize(3);
    }

    @Test
    public void save() throws Throwable {
        ChatPhoto chatPhoto = ChatPhoto.builder()
                .id(PHOTO_ID)
                .uri(PHOTO_URI)
                .build();

        mDbChatPhotoRepo.save(chatPhoto);

        ChatPhoto savedChatPhoto = mDbChatPhotoRepo.get(PHOTO_ID);

        assertThat(savedChatPhoto).isEqualTo(chatPhoto);
    }

    @Test
    public void saveCollection() throws Throwable {
        List<ChatPhoto> chatPhotos = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            ChatPhoto chatPhoto = ChatPhoto.builder()
                    .uri(PHOTO_URI + "_" + i)
                    .build();
            chatPhotos.add(chatPhoto);
        }

        mDbChatPhotoRepo.save(chatPhotos);

        List<ChatPhoto> savedChatPhotos = mDbChatPhotoRepo.getAll();
        assertThat(savedChatPhotos).hasSize(3);

        for (ChatPhoto chatPhoto : savedChatPhotos) {
            assertThat(chatPhoto.getId()).isNotNull();
        }
    }

    @Test
    public void delete() throws Exception {
        populateTable(1);

        ChatPhoto chatPhoto = mDbChatPhotoRepo.get(PHOTO_ID);
        assertThat(chatPhoto).isNotNull();

        mDbChatPhotoRepo.delete(chatPhoto);
        chatPhoto = mDbChatPhotoRepo.get(PHOTO_ID);
        assertThat(chatPhoto).isNull();
    }

}