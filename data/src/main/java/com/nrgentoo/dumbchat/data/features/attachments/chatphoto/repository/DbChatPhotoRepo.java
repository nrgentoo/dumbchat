package com.nrgentoo.dumbchat.data.features.attachments.chatphoto.repository;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nrgentoo.dumbchat.data.core.db.DbContext;
import com.nrgentoo.dumbchat.data.features.attachments.chatphoto.model.ChatPhotoDbo;
import com.nrgentoo.dumbchat.data.features.attachments.chatphoto.model.ChatPhotoMapper;
import com.nrgentoo.dumbchat.domain.core.repository.Repository;
import com.nrgentoo.dumbchat.domain.features.attachments.entity.ChatPhoto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

/**
 * Implementation of {@link Repository<ChatPhoto>}
 */

public class DbChatPhotoRepo implements Repository<ChatPhoto> {

    @Inject
    DbContext mDbContext;

    @Inject
    public DbChatPhotoRepo() {
    }

    @Override
    public ChatPhoto get(long id) {
        String[] selectionArgs = new String[]{String.valueOf(id)};
        Cursor cursor = mDbContext.getDatabase().rawQuery("SELECT * FROM " +
                ChatPhotoTable.TABLE_NAME +
                " WHERE " + ChatPhotoTable.COLUMN_ID + "=?", selectionArgs);

        ChatPhoto chatPhoto = null;
        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {
            ChatPhotoDbo chatPhotoDbo = ChatPhotoTable.parseCursor(cursor);
            chatPhoto = ChatPhotoMapper.transform(chatPhotoDbo);
        }
        cursor.close();

        return chatPhoto;
    }

    @Override
    public List<ChatPhoto> getAll() {
        Cursor cursor = mDbContext.getDatabase().rawQuery("SELECT * FROM " +
                ChatPhotoTable.TABLE_NAME, null);

        List<ChatPhoto> chatPhotos = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ChatPhotoDbo chatPhotoDbo = ChatPhotoTable.parseCursor(cursor);
            chatPhotos.add(ChatPhotoMapper.transform(chatPhotoDbo));
            cursor.moveToNext();
        }
        cursor.close();

        return chatPhotos;
    }

    @Override
    public void save(ChatPhoto chatPhoto) throws Throwable {
        long localId = mDbContext.getDatabase().insertWithOnConflict(ChatPhotoTable.TABLE_NAME,
                null, ChatPhotoTable.toContentValues(chatPhoto), SQLiteDatabase.CONFLICT_REPLACE);

        chatPhoto.setId(localId);
    }

    @Override
    public void save(Collection<ChatPhoto> chatPhotos) throws Throwable {
        for (ChatPhoto chatPhoto : chatPhotos) {
            save(chatPhoto);
        }
    }

    @Override
    public void delete(ChatPhoto chatPhoto) {
        String[] selectionArgs = new String[]{String.valueOf(chatPhoto.getId())};
        mDbContext.getDatabase().delete(ChatPhotoTable.TABLE_NAME, ChatPhotoTable.COLUMN_ID + "=?",
                selectionArgs);
    }
}
