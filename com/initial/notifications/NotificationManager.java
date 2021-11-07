package com.initial.notifications;

import java.util.concurrent.*;
import java.util.*;

public class NotificationManager
{
    public static LinkedBlockingQueue<Notification> pendingNotifications;
    public static Notification currentNotification;
    
    public static void show(final Notification notification) {
        NotificationManager.pendingNotifications.add(notification);
    }
    
    public static void update() {
        NotificationManager.pendingNotifications.removeIf(notif -> notif.timer.hasTimeElapsed(notif.end, false));
    }
    
    public static void render() {
        update();
        int count = 0;
        for (final Notification notif : NotificationManager.pendingNotifications) {
            ++count;
            notif.render(count);
        }
    }
    
    static {
        NotificationManager.pendingNotifications = new LinkedBlockingQueue<Notification>();
        NotificationManager.currentNotification = null;
    }
}
