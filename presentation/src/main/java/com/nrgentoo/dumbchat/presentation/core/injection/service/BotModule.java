package com.nrgentoo.dumbchat.presentation.core.injection.service;

import com.nrgentoo.dumbchat.data.features.bot.DbMessageIdService;
import com.nrgentoo.dumbchat.data.features.bot.MessageIdService;
import com.nrgentoo.dumbchat.data.features.bot.OfflineBotService;
import com.nrgentoo.dumbchat.domain.features.bot.BotService;
import com.nrgentoo.dumbchat.domain.features.chat.AppVisibilityService;
import com.nrgentoo.dumbchat.domain.features.notification.NotificationService;
import com.nrgentoo.dumbchat.presentation.features.chat.service.AppVisibilityServiceImpl;
import com.nrgentoo.dumbchat.presentation.features.notification.NotificationServiceImpl;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

/**
 * Module with bot service dependencies
 */

@Module
public class BotModule {

    @Provides
    BotService provideBotService(OfflineBotService botService) {
        return botService;
    }

    @Provides
    Scheduler provideScheduler() {
        return Schedulers.computation();
    }

    @Provides
    MessageIdService provideMessageIdService(DbMessageIdService messageIdService) {
        return messageIdService;
    }

    @Provides
    AppVisibilityService provideAppVisibilityService(
            AppVisibilityServiceImpl appVisibilityService) {
        return appVisibilityService;
    }

    @Provides
    NotificationService provideNotificationService(NotificationServiceImpl notificationService) {
        return notificationService;
    }
}
