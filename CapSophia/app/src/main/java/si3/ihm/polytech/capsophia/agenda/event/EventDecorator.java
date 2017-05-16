package si3.ihm.polytech.capsophia.agenda.event;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.util.List;

import si3.ihm.polytech.capsophia.R;

/**
 * Created by user on 10/05/2017.
 */

public class EventDecorator implements DayViewDecorator {

    private final List<CalendarDay> dates;
    private List<EventModel> events;
    private boolean displayLocal;

    public EventDecorator(List<CalendarDay> dates, List<EventModel> events) {
        this.dates = dates;
        this.events = events;
    }

    public void setDisplayLocal(boolean displayLocal) {
        this.displayLocal = displayLocal;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        if (displayLocal)
            return dates.contains(day);
        return dates.contains(day) && !events.get(dates.indexOf(day)).isOnLocal();
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new DotSpan(13f, R.color.colorSecondary));
    }
}