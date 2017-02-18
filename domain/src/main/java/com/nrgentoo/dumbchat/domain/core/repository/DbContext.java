package com.nrgentoo.dumbchat.domain.core.repository;

/**
 * Interface for database context
 */

public interface DbContext {

    void startTransaction();

    void markTransactionSuccessful();

    void endTransaction();
}
