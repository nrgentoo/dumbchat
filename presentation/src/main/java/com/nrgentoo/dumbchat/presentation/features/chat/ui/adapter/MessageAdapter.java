package com.nrgentoo.dumbchat.presentation.features.chat.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nrgentoo.dumbchat.R;
import com.nrgentoo.dumbchat.presentation.core.injection.activity.PerActivity;
import com.nrgentoo.dumbchat.presentation.features.chat.data.MessageVM;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Adapter to show a messages in chat
 */

@PerActivity
public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private static final int MESSAGE_LEFT = 0;
    private static final int MESSAGE_RIGHT = 1;

    private List<MessageVM> messageVMList;

    @Inject
    public MessageAdapter() {
    }

    public void setItems(List<MessageVM> messageVMList) {
        this.messageVMList = messageVMList;
    }

    @Override
    public int getItemViewType(int position) {
        MessageVM messageVM = messageVMList.get(position);

        if (messageVM.isMine()) {
            return MESSAGE_RIGHT;
        } else {
            return MESSAGE_LEFT;
        }
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View itemView;
        switch (viewType) {
            case MESSAGE_RIGHT:
                itemView = inflater.inflate(R.layout.item_message_right, parent, false);
                break;
            case MESSAGE_LEFT:
                itemView = inflater.inflate(R.layout.item_message_left, parent, false);
                break;
            default:
                throw new IllegalArgumentException("Unknown view type: " + viewType);
        }

        return new MessageViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {
        MessageVM messageVM = messageVMList.get(position);
        holder.bind(messageVM);
    }

    @Override
    public int getItemCount() {
        return (messageVMList != null) ? messageVMList.size() : 0;
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_avatar)
        ImageView ivAvatar;

        @BindView(R.id.tv_message)
        TextView tvMessage;

        @BindView(R.id.rv_photos)
        RecyclerView rvPhotos;

        public MessageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            rvPhotos.setNestedScrollingEnabled(false);
        }

        public void bind(MessageVM messageVM) {
            tvMessage.setText(messageVM.text());

            Glide.with(itemView.getContext())
                    .load(messageVM.author().avatarUri())
                    .centerCrop()
                    .into(ivAvatar);

            if (messageVM.attachments().isEmpty()) {
                rvPhotos.setVisibility(View.GONE);
            } else {
                rvPhotos.setVisibility(View.VISIBLE);
                // TODO set photos
            }
        }
    }
}
