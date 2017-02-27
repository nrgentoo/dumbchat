package com.nrgentoo.dumbchat.presentation.core.injection.service;

import com.nrgentoo.dumbchat.presentation.core.injection.application.ApplicationComponent;
import com.nrgentoo.dumbchat.presentation.features.chat.service.DumbChatService;

import dagger.Component;

/**
 * Component for service scope
 */

@PerService
@Component(dependencies = ApplicationComponent.class, modules = { ServiceModule.class,
        BotModule.class})
public interface ServiceComponent {

    void inject(DumbChatService dumbChatService);
}
