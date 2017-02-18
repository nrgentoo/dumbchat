package com.nrgentoo.dumbchat.domain.features.attachments.entity;

import javax.annotation.Nullable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Builder;

/**
 * Photo entity
 */

@Builder
@ToString(exclude = "id")
@EqualsAndHashCode(exclude = "id")
public class ChatPhoto {

    @Nullable
    @Getter @Setter private Long id;

    @Getter String uri;
}
