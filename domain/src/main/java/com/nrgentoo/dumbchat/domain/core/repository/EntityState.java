package com.nrgentoo.dumbchat.domain.core.repository;

import com.google.auto.value.AutoValue;

/**
 * Entity state
 */

@AutoValue
public abstract class EntityState<T> {

    public abstract String state();

    public abstract T entity();

    public static <T> Builder<T> builder() {
        return new AutoValue_EntityState.Builder<>();
    }

    @AutoValue.Builder
    public abstract static class Builder<T> {

        public abstract Builder<T> setState(String state);

        public abstract Builder<T> setEntity(T entity);

        public abstract EntityState<T> build();
    }
}
