package com.nrgentoo.dumbchat.presentation.features.chat.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.nrgentoo.dumbchat.R;
import com.nrgentoo.dumbchat.presentation.core.injection.activity.PerActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Adapter to show photo attachments
 */

@PerActivity
public class PhotoAttachmentRemovableAdapter
        extends RecyclerView.Adapter<PhotoAttachmentRemovableAdapter.PhotoViewHolder> {

    private List<String> mPhotoUris;

    private OnRemoveClickListener mOnRemoveClickListener;

    @Inject
    public PhotoAttachmentRemovableAdapter() {
    }

    public void setItems(List<String> photoUris) {
        this.mPhotoUris = photoUris;
    }

    public void setmOnRemoveClickListener(OnRemoveClickListener listener) {
        this.mOnRemoveClickListener = listener;
    }

    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_photo_removable, parent, false);
        return new PhotoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PhotoViewHolder holder, int position) {
        holder.bind(mPhotoUris.get(position));
    }

    @Override
    public int getItemCount() {
        return mPhotoUris != null ? mPhotoUris.size() : 0;
    }

    public class PhotoViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_photo)
        ImageView ivPhoto;

        @BindView(R.id.ib_remove)
        ImageButton ibRemove;

        public PhotoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.ib_remove)
        void onRemoveClicked() {
            if (mOnRemoveClickListener != null) {
                mOnRemoveClickListener.onRemoveClicked(getAdapterPosition());
            }
        }

        public void bind(String photoUrl) {
            Glide.with(itemView.getContext())
                    .load(photoUrl)
                    .centerCrop()
                    .into(ivPhoto);
        }
    }

    public interface OnRemoveClickListener {

        void onRemoveClicked(int removePosition);
    }
}
