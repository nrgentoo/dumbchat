package com.nrgentoo.dumbchat.presentation.features.chat.data;

import com.nrgentoo.dumbchat.domain.features.messages.entity.Message;
import com.nrgentoo.dumbchat.domain.features.users.entity.User;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Map message from domain to presentation
 */

public final class MessageMapperVM {

    private MessageMapperVM() {
    }

    /**
     * Map a {@link Message} from domain layer to a {@link MessageVM} from presentation layer
     *
     * @param message message from domain layer to transform
     * @param myself user object representing myself
     * @param dateFormat date format to transform timeStamp to dateTime
     */
    public static MessageVM transform(Message message, User myself, DateFormat dateFormat) {
        return MessageVM.builder()
                .setId(message.getId())
                .setAuthor(UserMapperVM.transform(message.getAuthor()))
                .setIsMine(message.getAuthor().equals(myself))
                .setText(message.getText())
                .setDateTime(dateFormat.format(new Date(message.getTimeStamp())))
                .setAttachments(AttachmentMapperVM.transform(message.getAttachments()))
                .build();
    }

    /**
     * Map a list of {@link Message} from domain layer to a list of {@link MessageVM} from
     * presentation layer
     *
     * @param messages messages from domain layer to transform
     * @param myself user object representing myself
     * @param dateFormat date format to transform timeStamp to dateTime
     */
    public static List<MessageVM> transform(List<Message> messages, User myself,
                                            DateFormat dateFormat) {
        List<MessageVM> messageVMs = new ArrayList<>();

        for (Message message : messages) {
            messageVMs.add(transform(message, myself, dateFormat));
        }

        return messageVMs;
    }
}
