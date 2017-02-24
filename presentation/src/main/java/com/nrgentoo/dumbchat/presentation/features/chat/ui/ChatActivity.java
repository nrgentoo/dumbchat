package com.nrgentoo.dumbchat.presentation.features.chat.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.nrgentoo.dumbchat.presentation.core.ui.BaseActivity;

import javax.inject.Inject;

/**
 * Chat activity
 */

public class ChatActivity extends BaseActivity implements ChatView {

    @Inject
    ChatPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivityComponent().inject(this);
        mPresenter.attachView(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    // --------------------------------------------------------------------------------------------
    //      VIEW INTERFACE
    // --------------------------------------------------------------------------------------------

    @Override
    public void notifyMessagesInserted(int insertPosition, int count) {

    }

    @Override
    public void notifyPhotoAppended(int insertPosition) {

    }

    @Override
    public void notifyPhotoRemoved(int photoIndex) {

    }

    @Override
    public String getTypedText() {
        return null;
    }
}
