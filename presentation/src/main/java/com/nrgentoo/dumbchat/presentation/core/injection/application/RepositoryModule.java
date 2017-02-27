package com.nrgentoo.dumbchat.presentation.core.injection.application;

import com.nrgentoo.dumbchat.data.features.attachments.attachment.repository.DbAttachmentRepo;
import com.nrgentoo.dumbchat.data.features.messages.cloud.ChatApi;
import com.nrgentoo.dumbchat.data.features.messages.cloud.OfflineChatApi;
import com.nrgentoo.dumbchat.data.features.messages.repository.MessageRepositoryImpl;
import com.nrgentoo.dumbchat.data.features.users.repository.DbUserRepo;
import com.nrgentoo.dumbchat.domain.core.repository.UnitOfWork;
import com.nrgentoo.dumbchat.domain.core.repository.UnitOfWorkImpl;
import com.nrgentoo.dumbchat.domain.features.attachments.repository.AttachmentRepo;
import com.nrgentoo.dumbchat.domain.features.messages.repository.MessageRepo;
import com.nrgentoo.dumbchat.domain.features.users.repository.UserRepo;

import dagger.Module;
import dagger.Provides;

/**
 * Module for providing repositories
 */

@Module
public class RepositoryModule {

    @Provides
    MessageRepo provideMessageRepo(MessageRepositoryImpl messageRepository) {
        return messageRepository;
    }

    @Provides
    ChatApi provideChatApi(OfflineChatApi chatApi) {
        return chatApi;
    }

    @Provides
    UserRepo provideUserRepo(DbUserRepo userRepo) {
        return userRepo;
    }

    @Provides
    AttachmentRepo provideAttachmentRepo(DbAttachmentRepo attachmentRepo) {
        return attachmentRepo;
    }

    @Provides
    UnitOfWork provideUnitOfWork(UnitOfWorkImpl unitOfWork) {
        return unitOfWork;
    }
}
