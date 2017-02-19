package com.nrgentoo.dumbchat.data.features.users.repository;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nrgentoo.dumbchat.data.core.db.DbContext;
import com.nrgentoo.dumbchat.data.features.users.model.UserDbo;
import com.nrgentoo.dumbchat.data.features.users.model.UserMapper;
import com.nrgentoo.dumbchat.domain.features.users.entity.User;
import com.nrgentoo.dumbchat.domain.features.users.repository.UserRepo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

/**
 * Database implementation of {@link UserRepo}
 */

public class DbUserRepo implements UserRepo {

    @Inject
    DbContext mDbContext;

    @Inject
    public DbUserRepo() {
    }

    @Override
    public User get(long id) {
        String[] selectionArgs = new String[]{String.valueOf(id)};
        Cursor cursor = mDbContext.getDatabase().rawQuery("SELECT * FROM " + UserTable.TABLE_NAME +
                " WHERE " + UserTable.COLUMN_ID + "=?", selectionArgs);

        User user = null;
        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {
            UserDbo userDbo = UserTable.parseCursor(cursor);
            user = UserMapper.transform(userDbo);
        }
        cursor.close();

        return user;
    }

    @Override
    public List<User> getAll() {
        Cursor cursor = mDbContext.getDatabase().rawQuery("SELECT * FROM " + UserTable.TABLE_NAME,
                null);

        List<User> users = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            UserDbo userDbo = UserTable.parseCursor(cursor);
            users.add(UserMapper.transform(userDbo));
            cursor.moveToNext();
        }
        cursor.close();

        return users;
    }

    @Override
    public void save(User user) throws Throwable {
        long localId = mDbContext.getDatabase().insertWithOnConflict(UserTable.TABLE_NAME, null,
                UserTable.toContentValues(user), SQLiteDatabase.CONFLICT_REPLACE);

        user.setId(localId);
    }

    @Override
    public void save(Collection<User> users) throws Throwable {
        for (User user : users) {
            save(user);
        }
    }

    @Override
    public void delete(User user) {
        String[] selectionArgs = new String[]{String.valueOf(user.getId())};
        mDbContext.getDatabase().delete(UserTable.TABLE_NAME,
                UserTable.COLUMN_ID + "=?", selectionArgs);
    }
}
