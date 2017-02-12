package com.nrgentoo.dumbchat.domain.features.users.entity;

import com.google.auto.value.AutoValue;

/**
 * User entity
 */

@AutoValue
public abstract class User {

    public abstract long id();

    public abstract String name();

    public abstract String avatarUri();

    public static Builder builder() {
        return new AutoValue_User.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {

        public abstract Builder setId(long id);

        public abstract Builder setName(String name);

        public abstract Builder setAvatarUri(String avatarUri);

        public abstract User build();
    }
}
