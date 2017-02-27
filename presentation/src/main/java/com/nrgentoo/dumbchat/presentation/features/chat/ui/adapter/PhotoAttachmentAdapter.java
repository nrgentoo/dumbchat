package com.nrgentoo.dumbchat.presentation.features.chat.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.nrgentoo.dumbchat.R;
import com.nrgentoo.dumbchat.presentation.features.chat.data.AttachmentVM;
import com.nrgentoo.dumbchat.presentation.features.chat.data.ChatPhotoVM;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Adapter to show photo attachments in message
 */

public class PhotoAttachmentAdapter
        extends RecyclerView.Adapter<PhotoAttachmentAdapter.PhotoViewHolder> {

    private List<AttachmentVM<?>> mAttachments;

    @Inject
    public PhotoAttachmentAdapter() {
    }

    public void setAttachments(List<AttachmentVM<?>> attachments) {
        this.mAttachments = attachments;
    }

    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_attachment_photo, parent, false);
        return new PhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PhotoViewHolder holder, int position) {
        holder.bind(mAttachments.get(position));
    }

    @Override
    public int getItemCount() {
        return mAttachments != null ? mAttachments.size() : 0;
    }

    public class PhotoViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_photo)
        ImageView ivPhoto;

        public PhotoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(AttachmentVM<?> attachmentVM) {
            ChatPhotoVM chatPhoto = (ChatPhotoVM) attachmentVM.attachment();

            Glide.with(itemView.getContext())
                    .load(chatPhoto.uri())
                    .centerCrop()
                    .into(ivPhoto);
        }
    }
}
