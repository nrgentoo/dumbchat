package com.nrgentoo.dumbchat.presentation.features.chat.data;

import com.nrgentoo.dumbchat.domain.features.users.entity.User;

/**
 * Map user from domain to presentation
 */

public final class UserMapperVM {

    private UserMapperVM() {
    }

    /**
     * Map a {@link User} from domain layer to a {@link UserVM} from presentation layer
     *
     * @param user user from domain layer to be transformed
     */
    public static UserVM transform(User user) {
        return UserVM.builder()
                .setId(user.getId())
                .setName(user.getName())
                .setAvatarUri(user.getAvatarUri())
                .build();
    }
}
