package com.nrgentoo.dumbchat.presentation.features.chat.data;

import com.google.auto.value.AutoValue;

/**
 * User view model
 */

@AutoValue
public abstract class UserVM {

    public abstract long id();

    public abstract String name();

    public abstract String avatarUri();

    public static Builder builder() {
        return new AutoValue_UserVM.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {

        public abstract Builder setId(long id);

        public abstract Builder setName(String name);

        public abstract Builder setAvatarUri(String avatarUri);

        public abstract UserVM build();
    }
}
