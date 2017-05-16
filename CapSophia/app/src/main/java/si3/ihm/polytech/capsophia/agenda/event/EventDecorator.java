package si3.ihm.polytech.capsophia.agenda.event;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.util.Calendar;
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
        Calendar ref = day.getCalendar();
        for (EventModel event: events) {
            if (ref.get(Calendar.YEAR) == event.getStartDate().get(Calendar.YEAR) &&
                    ref.get(Calendar.DAY_OF_YEAR) == event.getStartDate().get(Calendar.DAY_OF_YEAR)) {
                if (!event.isOnLocal())
                    return true;
            }
        }
        return false;
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new DotSpan(13f, R.color.colorSecondary));
    }
}