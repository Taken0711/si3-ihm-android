package si3.ihm.polytech.capsophia.notification;

import si3.ihm.polytech.capsophia.R;

/**
 * Created by jerem on 14/05/2017.
 */

public enum NotificationType {
    EVENT(R.string.notif_event_title, R.string.notif_event_desc),
    FLASH(R.string.notif_flash_title, R.string.notif_flash_desc),
    OPEN_CLOSE(R.string.notif_oc_title, R.string.notif_oc_desc);

    private int title;
    private int description;

    NotificationType(int title, int description) {
        this.title = title;
        this.description = description;
    }

    public int getTitle() {
        return title;
    }

    public int getId() {
        return ordinal();
    }

    public int getDescription() {
        return description;
    }
}
