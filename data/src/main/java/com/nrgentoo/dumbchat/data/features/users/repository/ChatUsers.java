package com.nrgentoo.dumbchat.data.features.users.repository;

import android.database.sqlite.SQLiteDatabase;

import com.nrgentoo.dumbchat.domain.features.users.entity.User;

/**
 * Helper class to insert chat users
 */

public final class ChatUsers {

    public static final User BOT_USER;
    public static final User CHAT_USER;

    static {
        BOT_USER = User.builder()
                .id(1L)
                .name("Bot")
                .avatarUri("https://www.gravatar.com/avatar/2?s=128&d=identicon&r=PG")
                .build();

        CHAT_USER = User.builder()
                .id(2L)
                .name("Mr. Smith")
                .avatarUri("https://www.gravatar.com/avatar/1?s=128&d=identicon&r=PG")
                .build();
    }

    public static void insetUsers(SQLiteDatabase db) {
        insert(BOT_USER, db);
        insert(CHAT_USER, db);
    }

    private static void insert(User user, SQLiteDatabase db) {
        db.insertWithOnConflict(UserTable.TABLE_NAME, null, UserTable.toContentValues(user),
                SQLiteDatabase.CONFLICT_REPLACE);
    }
}
