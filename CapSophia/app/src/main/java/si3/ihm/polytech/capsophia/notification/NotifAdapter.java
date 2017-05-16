package si3.ihm.polytech.capsophia.notification;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Switch;

import si3.ihm.polytech.capsophia.R;

/**
 * Created by user on 08/05/2017.
 */

public class NotifAdapter extends ArrayAdapter<NotificationType> {


    public NotifAdapter(@NonNull Context context, @NonNull NotificationType[] objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.notif, parent, false);
        }

        final NotificationType p = getItem(position);

        Switch s = (Switch) v.findViewById(R.id.notif_switch);
        s.setText(getContext().getString(p.getDescription()));
        s.setChecked(true);
        s.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                NotificationManager.setAutorisation(p, isChecked);
            }
        });

        return v;
    }
}
