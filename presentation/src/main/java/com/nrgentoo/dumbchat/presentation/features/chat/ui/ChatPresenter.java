package com.nrgentoo.dumbchat.presentation.features.chat.ui;

import android.annotation.SuppressLint;

import com.nrgentoo.dumbchat.domain.core.usecase.UseCaseExecutor;
import com.nrgentoo.dumbchat.domain.features.messages.entity.Message;
import com.nrgentoo.dumbchat.domain.features.messages.usecase.GetMessagesUseCase;
import com.nrgentoo.dumbchat.domain.features.messages.usecase.PostMessageUseCase;
import com.nrgentoo.dumbchat.domain.features.users.entity.User;
import com.nrgentoo.dumbchat.domain.features.users.usecase.GetMyselfUseCase;
import com.nrgentoo.dumbchat.presentation.core.injection.configpersistent.ConfigPersistent;
import com.nrgentoo.dumbchat.presentation.core.ui.BasePresenter;
import com.nrgentoo.dumbchat.presentation.features.chat.data.MessageMapperVM;
import com.nrgentoo.dumbchat.presentation.features.chat.data.MessageVM;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;

import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.subscribers.DisposableSubscriber;

/**
 * Presenter for {@link ChatView}
 */

@ConfigPersistent
class ChatPresenter extends BasePresenter<ChatView> {

    @SuppressLint("SimpleDateFormat")
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("HH:mm:ss");

    @Inject
    GetMessagesUseCase mGetMessagesUseCase;

    @SuppressWarnings("WeakerAccess")
    @Inject
    UseCaseExecutor mGetMessagesExecutor;

    @Inject
    Provider<PostMessageUseCase> mPostMessageUseCaseProvider;

    @Inject
    UseCaseExecutor mPostMessageExecutor;

    private Single<User> mMyselfSingle;

    List<MessageVM> mMessages;

    @Inject
    ChatPresenter() {
    }

    @Inject
    void setGetMyselfUseCase(GetMyselfUseCase useCase) {
        mMyselfSingle = useCase.execute(null).cache();
    }

    @Override
    public void attachView(ChatView mvpView) {
        super.attachView(mvpView);

        getMessages();
    }

    @Override
    public void detachView() {
        super.detachView();
        mGetMessagesExecutor.unsubscribe();
    }

    private void getMessages() {
        mGetMessagesExecutor.unsubscribe();

        Flowable<List<MessageVM>> messagesFlowable = mGetMessagesUseCase.execute(null)
                .flatMapSingle(messages -> mMyselfSingle
                        .map(user -> MessageMapperVM.transform(messages, user, DATE_FORMAT)));

        mGetMessagesExecutor.execute(messagesFlowable, new MessagesSubscriber());
    }

    /**
     * Messages subscriber
     */
    private class MessagesSubscriber extends DisposableSubscriber<List<MessageVM>> {

        @Override
        public void onNext(List<MessageVM> messages) {
            if (mMessages == null) {
                mMessages = new LinkedList<>();
            }
            int insertPos = mMessages.size();
            int insertCount = messages.size();
            mMessages.addAll(messages);

            getMvpView().notifyMassagesInserted(insertPos, insertCount);
        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onComplete() {

        }
    }

    public void postMessage(String message) {
        PostMessageUseCase useCase = mPostMessageUseCaseProvider.get();

        Single<Message> postMessageSingle = mMyselfSingle
                .map(user -> PostMessageUseCase.Params.builder()
                .setMessageText(message)
                .setAuthorId(user.getId())
                .build())
                .flatMap(useCase::execute);

        mPostMessageExecutor.execute(postMessageSingle, new PostMessageObserver());
    }

    private class PostMessageObserver extends DisposableSingleObserver<Message> {

        @Override
        public void onSuccess(Message message) {

        }

        @Override
        public void onError(Throwable e) {

        }
    }
}
