package com.nrgentoo.dumbchat.domain.features.chat;

import com.nrgentoo.dumbchat.domain.core.event.EventsPort;
import com.nrgentoo.dumbchat.domain.core.repository.UnitOfWork;
import com.nrgentoo.dumbchat.domain.features.bot.BotService;
import com.nrgentoo.dumbchat.domain.features.messages.entity.Message;
import com.nrgentoo.dumbchat.domain.features.messages.event.NewMessageEvent;
import com.nrgentoo.dumbchat.domain.features.notification.NotificationService;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.subscribers.DisposableSubscriber;

/**
 * Offline {@link ChatService} implementation
 */
public class OfflineChatService implements ChatService {

    @Inject
    BotService mBotService;

    @SuppressWarnings("WeakerAccess")
    @Inject
    EventsPort mEventsPort;

    @SuppressWarnings("WeakerAccess")
    @Inject
    UnitOfWork mUnitOfWork;

    @Inject
    AppVisibilityService mAppVisibilityService;

    @Inject
    NotificationService mNotificationService;

    private Disposable mMessagesSubscription;

    @Inject
    public OfflineChatService() {
    }

    @Override
    public void connect() {
        mMessagesSubscription = mBotService.messages()
                .subscribeWith(new BotMessagesSubscriber());
    }

    @Override
    public void disconnect() {
        if (mMessagesSubscription != null && !mMessagesSubscription.isDisposed()) {
            mMessagesSubscription.dispose();
        }
    }

    /**
     * Subscriber to bot messages
     */
    private class BotMessagesSubscriber extends DisposableSubscriber<Message> {

        @Override
        public void onNext(Message message) {
            // insert new message and all relations
            mUnitOfWork.insert(message);
            try {
                // commit transaction
                mUnitOfWork.commit();

                // post event
                //noinspection ConstantConditions
                NewMessageEvent event = NewMessageEvent.builder()
                        .setMessageId(message.getId())
                        .build();
                mEventsPort.broadcast(NewMessageEvent.class, event);

                if (mAppVisibilityService.isInBackground()) {
                    mNotificationService.notifyNewMessage(message);
                }
            } catch (Throwable throwable) {
//                Log.e(TAG, "Error while inserting new message from Bot", throwable);
            }
        }

        @Override
        public void onError(Throwable t) {

        }

        @Override
        public void onComplete() {

        }
    }
}
