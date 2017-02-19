package com.nrgentoo.dumbchat.domain.features.attachments.repository;

import com.nrgentoo.dumbchat.domain.core.repository.Repository;
import com.nrgentoo.dumbchat.domain.features.attachments.entity.Attachment;
import com.nrgentoo.dumbchat.domain.features.attachments.entity.AttachmentEntity;

import java.util.List;

/**
 * {@link Repository} for {@link Attachment}
 */

public interface AttachmentRepo extends Repository<Attachment<? extends AttachmentEntity>> {

    List<Attachment<?>> getForMessage(long messageId);
}
