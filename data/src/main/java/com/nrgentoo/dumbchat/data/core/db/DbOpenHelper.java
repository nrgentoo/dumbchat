package com.nrgentoo.dumbchat.data.core.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.nrgentoo.dumbchat.data.features.attachments.attachment.repository.AttachmentTable;
import com.nrgentoo.dumbchat.data.features.attachments.chatphoto.repository.ChatPhotoTable;
import com.nrgentoo.dumbchat.data.features.messages.repository.MessageTable;
import com.nrgentoo.dumbchat.data.features.users.repository.ChatUsers;
import com.nrgentoo.dumbchat.data.features.users.repository.UserTable;

/**
 * Database open helper
 */

public class DbOpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "chat_app";
    private static final int DATABASE_VERSION = 1;

    public DbOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.beginTransaction();
        try {
            // version 1
            db.execSQL(UserTable.CREATE);
            db.execSQL(ChatPhotoTable.CREATE);
            db.execSQL(AttachmentTable.CREATE);
            db.execSQL(MessageTable.CREATE);
            ChatUsers.insetUsers(db);

            // mark transaction completed
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.beginTransaction();
        try {
            switch (oldVersion) {
                case 1:
                    // upgrade to version 2
            }

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }
}
