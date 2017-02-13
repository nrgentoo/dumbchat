package com.nrgentoo.dumbchat.domain.features.messages.usecase;

import com.nrgentoo.dumbchat.domain.core.event.EventCallback;
import com.nrgentoo.dumbchat.domain.core.event.EventsPort;
import com.nrgentoo.dumbchat.domain.core.executor.PostExecutionThread;
import com.nrgentoo.dumbchat.domain.core.executor.ThreadExecutor;
import com.nrgentoo.dumbchat.domain.core.usecase.UseCase;
import com.nrgentoo.dumbchat.domain.features.messages.entity.Message;
import com.nrgentoo.dumbchat.domain.features.messages.event.NewMessageEvent;
import com.nrgentoo.dumbchat.domain.features.messages.repository.MessageRepo;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;

/**
 * {@link UseCase} to get messages
 */

public class GetMessagesUseCase extends UseCase<List<Message>, Void> {

    private MessageRepo mMessageRepo;
    private EventsPort mEventsPort;

    @Inject
    public GetMessagesUseCase(ThreadExecutor threadExecutor,
                              PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
    }

    @Inject
    public void setMessageRepo(MessageRepo messageRepo) {
        this.mMessageRepo = messageRepo;
    }

    @Inject
    public void setEventsPort(EventsPort eventsPort) {
        this.mEventsPort = eventsPort;
    }

    @Override
    protected Flowable<List<Message>> buildObservable(Void params) {
        return Flowable.create(e -> {
            if (e.isCancelled()) return;

            // first push all messages from repository
            e.onNext(mMessageRepo.getAll());

            // subscribe to new message event
            EventCallback<NewMessageEvent> listener = event -> {
                // and push new message
                e.onNext(Collections.singletonList(mMessageRepo.get(event.messageId())));
            };

            // subscribe to event
            mEventsPort.subscribe(NewMessageEvent.class, listener);

            // set cancelable
            e.setCancellable(() -> mEventsPort.unsubscribe(NewMessageEvent.class, listener));
        }, BackpressureStrategy.BUFFER);
    }
}
