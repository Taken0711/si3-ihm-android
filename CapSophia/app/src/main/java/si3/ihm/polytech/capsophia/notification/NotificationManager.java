package si3.ihm.polytech.capsophia.notification;

import android.content.Context;
import android.support.v4.app.NotificationCompat;

import java.util.HashMap;
import java.util.Map;

import si3.ihm.polytech.capsophia.R;

/**
 * Created by jerem on 12/05/2017.
 */

public class NotificationManager {

    private static Map<NotificationType, Boolean> autorisationMap = new HashMap<>();

    static {
        for (NotificationType notif: NotificationType.values())
            autorisationMap.put(notif, true);
    }

    private NotificationManager() {}

    public static void setAutorisation(NotificationType notificationType, boolean autorisation) {
        autorisationMap.put(notificationType, autorisation);
    }

    public static boolean getAutorisation(NotificationType notificationType) {
        return autorisationMap.get(notificationType);
    }

    public static void notify(Context context, NotificationType notificationType, String contentText) {
        if (!autorisationMap.get(notificationType))
            return;
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(context.getString(notificationType.getTitle()))
                        .setContentText(contentText);
        android.app.NotificationManager mNotificationManager = (android.app.NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        // notificationID allows you to update the notification later on.
        mNotificationManager.notify(notificationType.getId(), mBuilder.build());
    }

}
