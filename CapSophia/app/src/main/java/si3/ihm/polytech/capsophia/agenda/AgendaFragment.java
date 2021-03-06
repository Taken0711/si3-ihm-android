package si3.ihm.polytech.capsophia.agenda;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import si3.ihm.polytech.capsophia.R;
import si3.ihm.polytech.capsophia.agenda.event.EventAdapter;
import si3.ihm.polytech.capsophia.agenda.event.EventDecorator;
import si3.ihm.polytech.capsophia.agenda.event.EventModel;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnAgendaFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AgendaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AgendaFragment extends Fragment implements OnDateSelectedListener {

    private OnAgendaFragmentInteractionListener mListener;
    private List<EventModel> events;
    private LocalCalendar lc;

    private boolean displayLocalEvents = false;
    private MaterialCalendarView materialCalendarView;
    private EventDecorator eventDecorator;

    public AgendaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AgendaFragment.
     */
    public static AgendaFragment newInstance() {
        AgendaFragment fragment = new AgendaFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_agenda, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        lc = new LocalCalendar(getActivity());

        lc.clearEvents();

        lc.addEvent();
        events = lc.readEvents();

        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();

        start.set(2017, 5, 9, 6, 0);
        end.set(2017, 5, 9, 7, 0);
        events.add(new EventModel("Livraison", start, end, "Livraison de matériel", false));

        materialCalendarView = (MaterialCalendarView) getView().findViewById(R.id.calendarView);

        List<CalendarDay> tmp = new ArrayList<>();

        for (EventModel event: events)
            tmp.add(CalendarDay.from(event.getStartDate()));


        eventDecorator = new EventDecorator(tmp, events);
        materialCalendarView.addDecorator(eventDecorator);
        materialCalendarView.setOnDateChangedListener(this);

        CheckBox cb = (CheckBox) getView().findViewById(R.id.localEvents);
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                displayLocalEvents = isChecked;
                eventDecorator.setDisplayLocal(isChecked);
                materialCalendarView.removeDecorators();
                materialCalendarView.addDecorator(eventDecorator);
                if(materialCalendarView.getSelectedDate() != null)
                    onDateSelected(materialCalendarView, materialCalendarView.getSelectedDate(), true);
            }
        });

        cb.setChecked(displayLocalEvents);
        eventDecorator.setDisplayLocal(displayLocalEvents);
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onAgendaFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnAgendaFragmentInteractionListener) {
            mListener = (OnAgendaFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnAgendaFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        TextView tv = (TextView) getView().findViewById(R.id.selectedDate);
        SimpleDateFormat formatter = new SimpleDateFormat(
                "dd/MM/yyyy", Locale.FRANCE);
        tv.setText(formatter.format(date.getCalendar().getTime()));

        Calendar ref = date.getCalendar();

        List<EventModel> eventThisDay = new LinkedList<>();

        for(EventModel event: events) {
            if (ref.get(Calendar.YEAR) == event.getStartDate().get(Calendar.YEAR) &&
                    ref.get(Calendar.DAY_OF_YEAR) == event.getStartDate().get(Calendar.DAY_OF_YEAR)) {
                if (displayLocalEvents)
                    eventThisDay.add(event);
                else if(!event.isOnLocal())
                    eventThisDay.add(event);
            }
        }

        Collections.sort(eventThisDay, new Comparator<EventModel>() {
            @Override
            public int compare(EventModel o1, EventModel o2) {
                return Long.compare(o1.getStartDate().getTimeInMillis(), o2.getStartDate().getTimeInMillis());
            }
        });

        LinearLayout eventList = (LinearLayout) getView().findViewById(R.id.eventList);

        eventList.removeAllViews();
        if (eventThisDay.isEmpty()) {
            TextView noEvent = new TextView(new ContextThemeWrapper(getContext(), R.style.subtitle2));
            noEvent.setText(R.string.event_none);
            noEvent.setGravity(Gravity.CENTER_HORIZONTAL);
            eventList.addView(noEvent);
        } else {
            EventAdapter adapter = new EventAdapter(getContext(), eventThisDay, lc);
            for(int i = 0 ; i < adapter.getCount(); i++)
                eventList.addView(adapter.getView(i, null, eventList));
        }

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnAgendaFragmentInteractionListener {
        void onAgendaFragmentInteraction(Uri uri);
    }
}
