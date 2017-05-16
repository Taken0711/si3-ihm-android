package si3.ihm.polytech.capsophia.agenda.event;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import si3.ihm.polytech.capsophia.R;
import si3.ihm.polytech.capsophia.agenda.LocalCalendar;

/**
 * Created by user on 08/05/2017.
 */

public class EventAdapter extends ArrayAdapter<EventModel> {

    private final LocalCalendar localCalendar;

    public EventAdapter(@NonNull Context context, @NonNull List<EventModel> objects, LocalCalendar localCalendar) {
        super(context, 0, objects);
        this.localCalendar = localCalendar;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.event, parent, false);
        }

        final EventModel p = getItem(position);

        TextView time = (TextView) v.findViewById(R.id.event_time);
        SimpleDateFormat formatter = new SimpleDateFormat(
                "hh:mm a", Locale.FRANCE);
        time.setText(formatter.format(p.getStartDate().getTime()) + "\n" +
                formatter.format(p.getEndDate().getTime()));
        TextView name = (TextView) v.findViewById(R.id.event_name);
        name.setText(p.getName());
        TextView desc = (TextView) v.findViewById(R.id.event_desc);
        desc.setText(p.getDescription());
        CheckBox synchro = (CheckBox) v.findViewById(R.id.event_synchro);
        synchro.setChecked(p.isSynchro());
        if (p.isOnLocal())
            synchro.setEnabled(false);

        synchro.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                p.setSynchro(isChecked);
                if (isChecked) {
                    localCalendar.addEvent(p);
                    Toast.makeText(buttonView.getContext(), R.string.add_event,
                            Toast.LENGTH_LONG).show();
                } else {
                    localCalendar.delEvent(p);
                    Toast.makeText(buttonView.getContext(), R.string.del_event,
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        return v;
    }
}
