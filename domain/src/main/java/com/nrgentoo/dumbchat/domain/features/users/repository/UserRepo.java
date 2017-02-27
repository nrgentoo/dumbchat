package com.nrgentoo.dumbchat.domain.features.users.repository;

import com.nrgentoo.dumbchat.domain.core.repository.Repository;
import com.nrgentoo.dumbchat.domain.features.users.entity.User;

/**
 * {@link Repository} for {@link User}
 */

public interface UserRepo extends Repository<User> {

    User getMyself();
}
