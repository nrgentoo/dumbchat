package com.nrgentoo.dumbchat.presentation.core.injection.application;

import com.nrgentoo.dumbchat.data.core.db.DbContext;
import com.nrgentoo.dumbchat.data.core.db.DbContextImpl;
import com.nrgentoo.dumbchat.domain.core.repository.PersistenceContext;

import dagger.Module;
import dagger.Provides;

/**
 * Module for providing database dependencies
 */

@Module
public class DbModule {

    @Provides
    @PerApplication
    DbContext provideDbContext(DbContextImpl dbContext) {
        return dbContext;
    }

    @Provides
    @PerApplication
    PersistenceContext providePersistenceContext(DbContext dbContext) {
        return dbContext;
    }
}
