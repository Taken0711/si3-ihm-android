package si3.ihm.polytech.capsophia.notification;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import si3.ihm.polytech.capsophia.R;

import static si3.ihm.polytech.capsophia.notification.NotificationType.*;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnNotifFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NotificationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotificationFragment extends Fragment {

    private OnNotifFragmentInteractionListener mListener;

    public NotificationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment NotificationFragment.
     */
    public static NotificationFragment newInstance() {
        NotificationFragment fragment = new NotificationFragment();
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
        return inflater.inflate(R.layout.fragment_notification, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Button notify = (Button) getView().findViewById(R.id.notify_button);

        // TODO: Notif icon is not appropriate

        LinearLayout notifTypeList = (LinearLayout) getView().findViewById(R.id.notif_type_list);

        NotifAdapter adapter = new NotifAdapter(getContext(), NotificationType.values());
        for(int i = 0 ; i < adapter.getCount(); i++)
            notifTypeList.addView(adapter.getView(i, null, notifTypeList));

        notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationManager.notify(getContext(), EVENT, "Distribution de goodies dans la boutique Superdry");
                NotificationManager.notify(getContext(), FLASH, "-10% sur le rayon chaussure dans la boutique H&M jusqu'à 17h");
                NotificationManager.notify(getContext(), OPEN_CLOSE, "Ouverture de la boutique Fnac ce soir 20h");
            }
        });
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onNotifFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnNotifFragmentInteractionListener) {
            mListener = (OnNotifFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnHomeFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
    public interface OnNotifFragmentInteractionListener {
        void onNotifFragmentInteraction(Uri uri);
    }
}
