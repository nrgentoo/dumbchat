package com.nrgentoo.dumbchat.presentation.features.chat.ui;

import com.nrgentoo.dumbchat.presentation.core.ui.MvpView;

/**
 * View interface of chat
 */

public interface ChatView extends MvpView {

    void notifyMessagesInserted(int insertPosition, int count);

    void notifyPhotoAppended(int insertPosition);

    void notifyPhotoRemoved(int photoIndex);
}
