package com.nrgentoo.dumbchat.domain.features.messages.usecase;

import com.google.auto.value.AutoValue;
import com.nrgentoo.dumbchat.domain.core.event.EventsPort;
import com.nrgentoo.dumbchat.domain.core.executor.PostExecutionThread;
import com.nrgentoo.dumbchat.domain.core.executor.ThreadExecutor;
import com.nrgentoo.dumbchat.domain.core.repository.UnitOfWork;
import com.nrgentoo.dumbchat.domain.core.usecase.SingleUseCase;
import com.nrgentoo.dumbchat.domain.features.attachments.entity.Attachment;
import com.nrgentoo.dumbchat.domain.features.messages.entity.Message;
import com.nrgentoo.dumbchat.domain.features.messages.event.NewMessageEvent;
import com.nrgentoo.dumbchat.domain.features.users.entity.User;

import java.util.Collections;
import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Inject;

import io.reactivex.Single;

/**
 * {@link SingleUseCase} to post message
 */

public class PostMessageUseCase extends SingleUseCase<Message, PostMessageUseCase.Params> {

    @Inject
    UnitOfWork mUnitOfWork;

    @Inject
    EventsPort mEventsPort;

    @Inject
    public PostMessageUseCase(ThreadExecutor threadExecutor,
                              PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
    }

    @Override
    protected Single<Message> buildSingle(Params params) {
        return Single.create(e -> {
            if (e.isDisposed()) return;

            Message message = createMessage(params);

            mUnitOfWork.insert(message);
            try {
                mUnitOfWork.commit();
                e.onSuccess(message);

                // push event
                //noinspection ConstantConditions
                NewMessageEvent event = NewMessageEvent.builder()
                        .setMessageId(message.getId())
                        .build();
                mEventsPort.broadcast(NewMessageEvent.class, event);
            } catch (Throwable throwable) {
                e.onError(throwable);
            }
        });
    }

    private Message createMessage(Params params) {
        User user = mUnitOfWork.getUserRepository().get(params.authorId());
        long timeStamp = System.currentTimeMillis();

        return Message.builder()
                .text(params.messageText())
                .author(user)
                .timeStamp(timeStamp)
                .attachments(params.attachments())
                .build();
    }

    @AutoValue
    public static abstract class Params {

        public abstract long authorId();

        @Nullable
        public abstract String messageText();

        public abstract List<Attachment<?>> attachments();

        public static Builder builder() {
            return new AutoValue_PostMessageUseCase_Params.Builder()
                    .setAttachments(Collections.emptyList());
        }

        @AutoValue.Builder
        public static abstract class Builder {

            public abstract Builder setAuthorId(long authorId);

            public abstract Builder setMessageText(@Nullable String messageText);

            public abstract Builder setAttachments(List<Attachment<?>> attachments);

            public abstract Params build();
        }
    }
}
