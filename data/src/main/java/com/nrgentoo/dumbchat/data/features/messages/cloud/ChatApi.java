package com.nrgentoo.dumbchat.data.features.messages.cloud;

import com.nrgentoo.dumbchat.domain.features.messages.entity.Message;

import java.util.List;

/**
 * Chat api to get messages from server
 */

public interface ChatApi {

    List<Message> getMessageHistory();
}
