package com.nrgentoo.dumbchat.data.features.messages.cloud;

import com.nrgentoo.dumbchat.data.features.users.repository.ChatUsers;
import com.nrgentoo.dumbchat.data.features.users.repository.DbUserRepo;
import com.nrgentoo.dumbchat.domain.features.messages.entity.Message;
import com.nrgentoo.dumbchat.domain.features.users.entity.User;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

/**
 * Offline implementation of {@link ChatApi}. Real implementation could use HTTP Rest api and
 * encapsulate all logic inside
 */

public class OfflineChatApi implements ChatApi {

    @Inject
    DbUserRepo mDbUserRepo;

    @Inject
    public OfflineChatApi() {
    }

    @Override
    public List<Message> getMessageHistory() {
        List<Message> messages = new LinkedList<>();

        User botUser = mDbUserRepo.get(ChatUsers.BOT_USER_ID);
        User myselfUser = mDbUserRepo.getMyself();

        Message message = Message.builder()
                .id(1L)
                .text("Привет. Сообщение с сервера")
                .timeStamp(System.currentTimeMillis() - 50_000L)
                .author(botUser)
                .attachments(Collections.emptyList())
                .build();
        messages.add(message);

        message = Message.builder()
                .id(2L)
                .text("Привет, бот.")
                .timeStamp(System.currentTimeMillis() - 45_000L)
                .author(myselfUser)
                .attachments(Collections.emptyList())
                .build();
        messages.add(message);

        message = Message.builder()
                .id(3L)
                .text("Я не бот. Я человек")
                .timeStamp(System.currentTimeMillis() - 30_000L)
                .author(botUser)
                .attachments(Collections.emptyList())
                .build();
        messages.add(message);

        message = Message.builder()
                .id(4L)
                .text("Может быть бот это ты? Докажи, что не бот.")
                .timeStamp(System.currentTimeMillis() - 25_000L)
                .author(botUser)
                .attachments(Collections.emptyList())
                .build();
        messages.add(message);

        message = Message.builder()
                .id(5L)
                .text("Слишком самоуверенный бот попался.")
                .timeStamp(System.currentTimeMillis() - 20_000L)
                .author(myselfUser)
                .attachments(Collections.emptyList())
                .build();
        messages.add(message);

        return messages;
    }
}
