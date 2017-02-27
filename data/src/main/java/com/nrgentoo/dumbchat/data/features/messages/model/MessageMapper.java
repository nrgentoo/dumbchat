package com.nrgentoo.dumbchat.data.features.messages.model;

import com.nrgentoo.dumbchat.domain.features.attachments.entity.Attachment;
import com.nrgentoo.dumbchat.domain.features.messages.entity.Message;
import com.nrgentoo.dumbchat.domain.features.users.entity.User;

import java.util.List;

/**
 * Message mapper
 */

public final class MessageMapper {

    private MessageMapper() {
    }

    /**
     * Map a {@link MessageDbo} from data layer to a {@link Message} from domain layer
     *
     * @param messageDbo message database object
     * @param author author of the message
     * @param attachments list of attachments
     */
    public static Message transform(MessageDbo messageDbo, User author,
                                    List<Attachment<?>> attachments) {
        return Message.builder()
                .id(messageDbo.id())
                .timeStamp(messageDbo.timeStamp())
                .text(messageDbo.text())
                .author(author)
                .attachments(attachments)
                .build();
    }
}
