package com.nrgentoo.dumbchat.data.features.users.model;

import com.google.auto.value.AutoValue;

/**
 * User data transfer object
 */

@AutoValue
public abstract class UserDto {

    public abstract long id();

    public abstract String name();

    public abstract String avatarUri();

    public static Builder builder() {
        return new AutoValue_UserDto.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {

        public abstract Builder setId(long id);

        public abstract Builder setName(String name);

        public abstract Builder setAvatarUri(String avatarUri);

        public abstract UserDto build();
    }
}
