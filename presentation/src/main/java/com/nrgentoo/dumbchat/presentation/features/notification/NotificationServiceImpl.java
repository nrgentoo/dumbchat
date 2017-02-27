package com.nrgentoo.dumbchat.presentation.features.notification;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.nrgentoo.dumbchat.R;
import com.nrgentoo.dumbchat.domain.features.messages.entity.Message;
import com.nrgentoo.dumbchat.domain.features.notification.NotificationService;
import com.nrgentoo.dumbchat.presentation.core.injection.service.QServiceContext;
import com.nrgentoo.dumbchat.presentation.features.chat.ui.ChatActivity;

import javax.inject.Inject;

/**
 *Implementation of {@link NotificationService}
 */

public class NotificationServiceImpl implements NotificationService {

    @Inject
    @QServiceContext
    Context mContext;

    @Inject
    public NotificationServiceImpl() {
    }

    @Override
    public void notifyNewMessage(Message message) {
        String title = mContext.getString(R.string.new_message_notification_title,
                message.getAuthor().getName());

        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(message.getText())
                .setTicker(message.getText())
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setContentIntent(getChatActivityPeinfingIntent());

        if (Build.VERSION.SDK_INT >= 21) builder.setVibrate(new long[0]);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(mContext);

        //noinspection ConstantConditions
        long messageId = message.getId();
        notificationManager.notify((int) messageId, builder.build());
    }

    private PendingIntent getChatActivityPeinfingIntent() {
        Intent intent = ChatActivity.getStartIntent(mContext);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        return PendingIntent.getActivity(mContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }
}
