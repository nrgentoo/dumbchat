package com.nrgentoo.dumbchat.presentation.features.chat.ui;

import com.nrgentoo.dumbchat.presentation.core.ui.MvpView;

/**
 * View interface of chat
 */

public interface ChatView extends MvpView {

    void notifyMassagesInserted(int insertPosition, int count);
}
