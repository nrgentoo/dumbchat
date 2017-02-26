package com.nrgentoo.dumbchat.presentation.features.chat.ui;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.nrgentoo.dumbchat.domain.core.usecase.UseCaseExecutor;
import com.nrgentoo.dumbchat.domain.features.attachments.entity.Attachment;
import com.nrgentoo.dumbchat.domain.features.attachments.entity.ChatPhoto;
import com.nrgentoo.dumbchat.domain.features.attachments.entity.PhotoAttachment;
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
    PostMessageUseCase mPostMessageUseCase;

    @Inject
    Provider<UseCaseExecutor> mExecutorProvider;

    private Single<User> mMyselfSingle;

    List<MessageVM> mMessages;
    List<String> mPhotoUris = new LinkedList<>();

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
                getMvpView().setMessages(mMessages);
            }
            int insertPos = mMessages.size();
            int insertCount = messages.size();
            mMessages.addAll(messages);

            getMvpView().notifyMessagesInserted(insertPos, insertCount);
        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onComplete() {

        }
    }

    public void appendPhoto(String photoUri) {
        int insertPos = mPhotoUris.size();
        mPhotoUris.add(photoUri);
        getMvpView().notifyPhotoAppended(insertPos);
    }

    public void removePhoto(int position) {
        mPhotoUris.remove(position);
        getMvpView().notifyPhotoRemoved(position);
    }

    public void postMessage() {
        if (validate()) {
            String text = getMvpView().getTypedText();

            if (mPhotoUris.isEmpty()) {
                postMessage(text);
            } else {
                postMessage(text, mPhotoUris);
            }
        }
    }

    private boolean validate() {
        String text = getMvpView().getTypedText();
        return !(text.isEmpty() && mPhotoUris.isEmpty());
    }

    void postMessage(String message) {
        postMessage(message, null);
    }

    void postMessage(String message, @Nullable List<String> photoUris) {
        Single<Message> postMessageSingle = mMyselfSingle
                .map(user -> PostMessageUseCase.Params.builder()
                        .setMessageText(message)
                        .setAttachments(makeAttachments(photoUris))
                        .setAuthorId(user.getId())
                        .build())
                .flatMap(mPostMessageUseCase::execute);

        UseCaseExecutor executor = mExecutorProvider.get();
        executor.execute(postMessageSingle, new PostMessageObserver(executor));
    }

    @NonNull
    private List<Attachment<?>> makeAttachments(List<String> photoUris) {
        List<Attachment<?>> attachments = new LinkedList<>();
        if (photoUris != null) {
            for (String photoUri : photoUris) {
                ChatPhoto chatPhoto = ChatPhoto.builder().uri(photoUri).build();
                PhotoAttachment photoAttachment = new PhotoAttachment(chatPhoto);
                attachments.add(photoAttachment);
            }
        }

        return attachments;
    }

    private class PostMessageObserver extends DisposableSingleObserver<Message> {

        final UseCaseExecutor mExecutor;

        private PostMessageObserver(UseCaseExecutor executor) {
            this.mExecutor = executor;
        }

        @Override
        public void onSuccess(Message message) {
            mExecutor.unsubscribe();
            getMvpView().scrollTo(mMessages.size());
        }

        @Override
        public void onError(Throwable e) {
            mExecutor.unsubscribe();
        }
    }
}
