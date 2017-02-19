package com.nrgentoo.dumbchat.data.features.users.repository;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.nrgentoo.dumbchat.data.core.db.DbContext;
import com.nrgentoo.dumbchat.data.core.db.DbOpenHelper;
import com.nrgentoo.dumbchat.domain.features.users.entity.User;

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
import static org.mockito.Mockito.when;

/**
 * Test {@link DbUserRepo}
 */

@RunWith(RobolectricTestRunner.class)
@Config(manifest= Config.NONE)
public class DbUserRepoTest {

    private static final long USER_ID = 1;
    private static final String USER_NAME = "mock user";
    private static final String USER_AVATAR_URI = "avatar uri";

    @Mock
    DbContext mockDbContext;

    private DbUserRepo mDbUserRepo;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        DbOpenHelper dbOpenHelper = new DbOpenHelper(RuntimeEnvironment.application);
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();

        when(mockDbContext.getDatabase())
                .thenReturn(db);

        mDbUserRepo = new DbUserRepo();
        mDbUserRepo.mDbContext = mockDbContext;

        clearDb();
    }

    @After
    public void tearDown() throws Exception {
        clearDb();
    }

    private void clearDb() {
        mockDbContext.getDatabase().delete(UserTable.TABLE_NAME, null, null);
    }

    @Test
    public void get() throws Exception {
        populateTable(1);

        User user = mDbUserRepo.get(USER_ID);

        assertThat(user.getId()).isEqualTo(USER_ID);
        assertThat(user.getName()).isEqualTo(USER_NAME);
        assertThat(user.getAvatarUri()).isEqualTo(USER_AVATAR_URI);
    }

    private void populateTable(int count) {
        for (int i = 0; i < count; i++) {
            ContentValues values = new ContentValues();
            values.put(UserTable.COLUMN_ID, USER_ID + i);
            values.put(UserTable.COLUMN_NAME, USER_NAME);
            values.put(UserTable.COLUMN_AVATAR_URI, USER_AVATAR_URI);

            mockDbContext.getDatabase().insert(UserTable.TABLE_NAME, null, values);
        }
    }

    @Test
    public void getAll() throws Exception {
        populateTable(3);

        List<User> users = mDbUserRepo.getAll();

        assertThat(users).hasSize(3);
    }

    @Test
    public void save() throws Throwable {
        User user = User.builder()
                .id(USER_ID)
                .name(USER_NAME)
                .avatarUri(USER_AVATAR_URI)
                .build();

        mDbUserRepo.save(user);

        User savedUser = mDbUserRepo.get(USER_ID);
        assertThat(savedUser).isEqualTo(user);
    }

    @Test
    public void saveCollection() throws Throwable {
        List<User> users = new LinkedList<>();
        for (int i = 0; i < 3; i++) {
            User user = User.builder()
                    .id(USER_ID + i)
                    .name(USER_NAME)
                    .avatarUri(USER_AVATAR_URI)
                    .build();
            users.add(user);
        }

        mDbUserRepo.save(users);

        List<User> savedUsers = mDbUserRepo.getAll();
        assertThat(savedUsers).containsAll(users);
    }

    @Test
    public void delete() throws Exception {
        populateTable(1);

        User user = mDbUserRepo.get(USER_ID);
        assertThat(user).isNotNull();

        mDbUserRepo.delete(user);
        user = mDbUserRepo.get(USER_ID);
        assertThat(user).isNull();
    }

}