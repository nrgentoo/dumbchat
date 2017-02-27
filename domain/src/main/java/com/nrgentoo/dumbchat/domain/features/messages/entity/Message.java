package com.nrgentoo.dumbchat.domain.features.messages.entity;

import com.nrgentoo.dumbchat.domain.features.attachments.entity.Attachment;
import com.nrgentoo.dumbchat.domain.features.attachments.entity.AttachmentEntity;
import com.nrgentoo.dumbchat.domain.features.users.entity.User;

import java.util.List;

import javax.annotation.Nullable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Builder;

/**
 * Message entity. {@link Message#getText()} and {@link #getAttachments()} can't be both null or empty
 */

@Builder
@ToString(exclude = "id")
@EqualsAndHashCode(exclude = "id")
public class Message {

    @Nullable
    @Getter @Setter private Long id;

    @Getter private String text;

    @Getter private User author;

    @Getter private long timeStamp;

    @Getter private List<Attachment<? extends AttachmentEntity>> attachments;
}
