package com.nrgentoo.dumbchat.data.core.db;

import android.database.sqlite.SQLiteDatabase;

import com.nrgentoo.dumbchat.domain.core.repository.PersistenceContext;

/**
 * Interface for database context. Extends {@link PersistenceContext}
 */

public interface DbContext extends PersistenceContext {

    SQLiteDatabase getDatabase();
}
