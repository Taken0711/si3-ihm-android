package si3.ihm.polytech.capsophia.agenda.event;

import java.util.Calendar;

/**
 * Created by user on 09/05/2017.
 */

public class EventModel {

    private String name;
    private Calendar startDate;
    private Calendar endDate;
    private String description;
    private final boolean onLocal;
    private boolean synchro;
    private long id;

    public EventModel(String name, Calendar startDate, Calendar endDate, String description, boolean onLocal) {
        this(name, startDate, endDate, description, onLocal, onLocal);
    }

    public EventModel(String name, Calendar startDate, Calendar endDate, String description, boolean onLocal, boolean synchro) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.onLocal = onLocal;
        this.synchro = synchro;
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

    public boolean isSynchro() {
        return synchro;
    }

    public void setSynchro(boolean synchro) {
        this.synchro = synchro;
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
