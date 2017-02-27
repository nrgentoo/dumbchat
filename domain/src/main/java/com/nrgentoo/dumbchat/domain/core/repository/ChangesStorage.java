package com.nrgentoo.dumbchat.domain.core.repository;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.inject.Inject;

/**
 * Keep track of changes in {@link UnitOfWork}
 */

class ChangesStorage implements Iterable<EntityState<?>> {

    private final Set<EntityState<?>> mEntityStates;

    @Inject
    ChangesStorage() {
        mEntityStates = new HashSet<>();
    }

    <T> void attach(T entity, String state) {
        //noinspection unchecked
        EntityState<T> entityState = (EntityState<T>) EntityState.builder()
                .setState(state)
                .setEntity(entity)
                .build();
        mEntityStates.add(entityState);
    }

    void clear() {
        mEntityStates.clear();
    }

    @Override
    public Iterator<EntityState<?>> iterator() {
        return mEntityStates.iterator();
    }
}
