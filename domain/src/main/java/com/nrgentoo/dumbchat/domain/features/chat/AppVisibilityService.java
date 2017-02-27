package com.nrgentoo.dumbchat.domain.features.chat;

/**
 * Get state of application visibility
 */

public interface AppVisibilityService {

    /**
     * Chek if app is in foreground
     *
     * @return true if app is in foreground or false if in background
     */
    boolean isInBackground();
}
