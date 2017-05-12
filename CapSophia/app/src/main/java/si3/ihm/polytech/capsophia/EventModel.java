package si3.ihm.polytech.capsophia;

import java.util.Calendar;

/**
 * Created by user on 09/05/2017.
 */

class EventModel {

    private String name;
    private Calendar startDate;
    private Calendar endDate;
    private String description;
    private boolean onLocal;
    private long id;

    public EventModel(String name, Calendar startDate, Calendar endDate, String description, boolean onLocal) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.onLocal = onLocal;
    }

    public String getName() {
        return name;
    }

    public Calendar getStartDate() {
        return startDate;
    }

    public Calendar getEndDate() {
        return endDate;
    }

    public String getDescription() {
        return description;
    }

    public boolean isOnLocal() {
        return onLocal;
    }

    public void setOnLocal(boolean onLocal) {
        this.onLocal = onLocal;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("EventModel{");
        sb.append("name='").append(name).append('\'');
        sb.append(", startDate='").append(startDate).append('\'');
        sb.append(", endDate='").append(endDate).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
