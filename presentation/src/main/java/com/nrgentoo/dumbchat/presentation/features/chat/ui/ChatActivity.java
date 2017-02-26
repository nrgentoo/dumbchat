package com.nrgentoo.dumbchat.presentation.features.chat.ui;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.nrgentoo.dumbchat.R;
import com.nrgentoo.dumbchat.presentation.core.ui.BaseActivity;
import com.nrgentoo.dumbchat.presentation.features.chat.data.MessageVM;
import com.nrgentoo.dumbchat.presentation.features.chat.ui.adapter.MessageAdapter;
import com.nrgentoo.dumbchat.presentation.features.chat.ui.adapter.PhotoAttachmentRemovableAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Chat activity
 */

public class ChatActivity extends BaseActivity implements ChatView {

    private static final int REQUEST_FROM_GALLERY = 100;

    @Inject
    ChatPresenter mPresenter;

    @Inject
    MessageAdapter mAdapter;

    @Inject
    PhotoAttachmentRemovableAdapter mPhotosAdapter;

    @BindView(R.id.rv_messages)
    RecyclerView rvMessages;

    @BindView(R.id.et_message)
    EditText etMessage;

    @BindView(R.id.rv_attached_photos)
    RecyclerView rvAttachedPhotos;

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
        // init messages recycler view
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        rvMessages.setLayoutManager(layoutManager);
        rvMessages.setAdapter(mAdapter);

        // init photos recycler view
        LinearLayoutManager photosLayoutManager = new LinearLayoutManager(this);
        rvAttachedPhotos.setLayoutManager(photosLayoutManager);
        rvAttachedPhotos.setAdapter(mPhotosAdapter);
        mPhotosAdapter.setmOnRemoveClickListener(mPresenter::removePhoto);
    }

    @OnClick(R.id.bt_send)
    void onSendClicked() {
        mPresenter.postMessage();
    }

    @OnClick(R.id.bt_attach)
    void onAttachClicked(View view) {
        PopupMenu menu = new PopupMenu(this, view);
        menu.inflate(R.menu.chat_attach_menu);

        menu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_attach_photo_from_camera:
                    return true;
                case R.id.menu_attach_photo_from_gallery:
                    launchGallery();
                    return true;
                default:
                    return false;
            }
        });

        menu.show();
    }

    private void launchGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_FROM_GALLERY);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_FROM_GALLERY:
                if (resultCode == RESULT_OK) onImagePicked(data);
                break;
        }
    }

    private void onImagePicked(Intent data) {
        Uri selectedImage = data.getData();
        String[] filePathColumn = { MediaStore.Images.Media.DATA };

        // get the cursor
        Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null,
                null, null);
        if (cursor != null) {
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String filePath = cursor.getString(columnIndex);
            cursor.close();

            mPresenter.appendPhoto(filePath);
        }
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
        rvAttachedPhotos.setVisibility(View.VISIBLE);
        mPhotosAdapter.notifyItemInserted(insertPosition);
    }

    @Override
    public void notifyPhotoRemoved(int photoIndex) {
        mPhotosAdapter.notifyItemRemoved(photoIndex);
        if (mPhotosAdapter.getItemCount() == 0) {
            rvAttachedPhotos.setVisibility(View.GONE);
        }
    }

    @Override
    public void notifyPhotosChanged() {
        mPhotosAdapter.notifyDataSetChanged();
        if (mPhotosAdapter.getItemCount() == 0) {
            rvAttachedPhotos.setVisibility(View.GONE);
        }
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
    public void scrollEnd() {
        rvMessages.smoothScrollToPosition(mAdapter.getItemCount());
    }

    @Override
    public void setPhotos(List<String> photoUris) {
        mPhotosAdapter.setItems(photoUris);
    }

    @Override
    public void clearText() {
        etMessage.setText(null);
    }
}
