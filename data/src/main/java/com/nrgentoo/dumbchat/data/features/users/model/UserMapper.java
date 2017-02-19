package com.nrgentoo.dumbchat.data.features.users.model;

import com.nrgentoo.dumbchat.domain.features.users.entity.User;

/**
 * User mapper
 */

public final class UserMapper {

    private UserMapper() {
    }

    /**
     * Map a {@link UserDbo} from data layer to a {@link User} from domain layer
     */
    public static User transform(UserDbo userDbo) {
        return User.builder()
                .id(userDbo.id())
                .name(userDbo.name())
                .avatarUri(userDbo.avatarUri())
                .build();
    }
}
