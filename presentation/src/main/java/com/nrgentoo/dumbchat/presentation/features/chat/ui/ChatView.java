package com.nrgentoo.dumbchat.presentation.features.chat.ui;

import com.nrgentoo.dumbchat.presentation.core.ui.MvpView;
import com.nrgentoo.dumbchat.presentation.features.chat.data.MessageVM;

import java.util.List;

/**
 * View interface of chat
 */

public interface ChatView extends MvpView {

    void notifyMessagesInserted(int insertPosition, int count);

    void notifyPhotoAppended(int insertPosition);

    void notifyPhotoRemoved(int photoIndex);

    void notifyPhotosChanged();

    String getTypedText();

    void setMessages(List<MessageVM> messages);

    void scrollEnd();

    void setPhotos(List<String> photoUris);

    void clearText();
}
