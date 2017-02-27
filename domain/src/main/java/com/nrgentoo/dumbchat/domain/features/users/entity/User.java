package com.nrgentoo.dumbchat.domain.features.users.entity;

import javax.annotation.Nullable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Builder;

/**
 * User entity
 */

@Builder
@ToString(exclude = "id")
@EqualsAndHashCode(exclude = "id")
public class User {

    @Nullable
    @Getter @Setter private Long id;

    @Getter private String name;

    @Getter private String avatarUri;

}
