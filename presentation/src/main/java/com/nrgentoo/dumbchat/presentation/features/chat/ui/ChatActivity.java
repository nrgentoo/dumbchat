package com.nrgentoo.dumbchat.presentation.features.chat.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;

import com.nrgentoo.dumbchat.R;
import com.nrgentoo.dumbchat.presentation.core.ui.BaseActivity;
import com.nrgentoo.dumbchat.presentation.features.chat.data.MessageVM;
import com.nrgentoo.dumbchat.presentation.features.chat.ui.adapter.MessageAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Chat activity
 */

public class ChatActivity extends BaseActivity implements ChatView {

    @Inject
    ChatPresenter mPresenter;

    @Inject
    MessageAdapter mAdapter;

    @BindView(R.id.rv_messages)
    RecyclerView rvMessages;

    @BindView(R.id.et_message)
    EditText etMessage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);

        getActivityComponent().inject(this);
        initViews();

        mPresenter.attachView(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    private void initViews() {
        rvMessages.setLayoutManager(new LinearLayoutManager(this));
        rvMessages.setAdapter(mAdapter);
    }

    @OnClick(R.id.bt_send)
    void onSendClicked() {
        mPresenter.postMessage();
        etMessage.setText(null);
    }

    @OnClick(R.id.bt_attach)
    void onAttachClicked() {
        // TODO show menu
    }

    // --------------------------------------------------------------------------------------------
    //      VIEW INTERFACE
    // --------------------------------------------------------------------------------------------

    @Override
    public void notifyMessagesInserted(int insertPosition, int count) {
        if (insertPosition == 0) {
            mAdapter.notifyDataSetChanged();
        } else {
            mAdapter.notifyItemRangeInserted(insertPosition, count);
        }
    }

    @Override
    public void notifyPhotoAppended(int insertPosition) {

    }

    @Override
    public void notifyPhotoRemoved(int photoIndex) {

    }

    @Override
    public String getTypedText() {
        return etMessage.getText().toString();
    }

    @Override
    public void setMessages(List<MessageVM> messages) {
        mAdapter.setItems(messages);
    }

    @Override
    public void scrollTo(int position) {
        rvMessages.smoothScrollToPosition(position);
    }
}
