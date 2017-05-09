package si3.ihm.polytech.capsophia;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.Arrays;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnHomeFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    private OnHomeFragmentInteractionListener mListener;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
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
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        List<NewsModel> highlights = Arrays.asList(
                new NewsModel("Les soldes arrivent à CapSophia !",
                        "Profiter de -30% à -60% dans tous vos magasins préférés",
                        R.drawable.soldes));
        final LinearLayout highlightList = (LinearLayout) getView().findViewById(R.id.highlightList);
        addAllNewsToLinearLayout(highlights, highlightList);
        List<NewsModel> stillOngoing = Arrays.asList(
                new NewsModel("Ouverture de la Fnac à CapSophia !",
                        "Découvrez nos univers: Livres, Papeterie, Kids, maison & design, objets connectés, smatphones, high tech, apple, son, musique, vidéo, TV, jeux vidéo, billetterie,..",
                        R.drawable.fnac),
                new NewsModel("C\'est bientôt l\'été !",
                        "L\'été s'invite à Cap Sophia. Découvrer nos animations et distributions de cadeaux dans les allées de votre centre commercial",
                        R.drawable.ete));
        final LinearLayout stillOngoingList = (LinearLayout) getView().findViewById(R.id.stillOngoingList);
        addAllNewsToLinearLayout(stillOngoing, stillOngoingList);
    }

    private void addAllNewsToLinearLayout(List<NewsModel> highlights, LinearLayout highlightList) {
        highlightList.removeAllViews();
        NewsAdapter adapter = new NewsAdapter(getContext(), highlights);
        for(int i = 0 ; i < adapter.getCount(); i++)
            highlightList.addView(adapter.getView(i, null, highlightList));
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onHomeFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnHomeFragmentInteractionListener) {
            mListener = (OnHomeFragmentInteractionListener) context;
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
public interface OnHomeFragmentInteractionListener {
        // TODO: Update argument type and name
        void onHomeFragmentInteraction(Uri uri);
    }
}
