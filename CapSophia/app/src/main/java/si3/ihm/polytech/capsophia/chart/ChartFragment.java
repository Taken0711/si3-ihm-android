package si3.ihm.polytech.capsophia.chart;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.ArrayList;

import si3.ihm.polytech.capsophia.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnChartFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ChartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChartFragment extends Fragment {

    private OnChartFragmentInteractionListener mListener;
    private BarChart barChart;

    public ChartFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ChartFragment.
     */
    public static ChartFragment newInstance() {
        ChartFragment fragment = new ChartFragment();
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
        return inflater.inflate(R.layout.fragment_chart, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        barChart = (BarChart) getView().findViewById(R.id.affluenceChart);

        ArrayList<BarEntry> entries = new ArrayList<>();
                entries.add(new BarEntry(0f, 12f));
                entries.add(new BarEntry(1f, 20f));
                entries.add(new BarEntry(2f, 32f));
                entries.add(new BarEntry(3f, 44f));
                entries.add(new BarEntry(4f, 57f));
                entries.add(new BarEntry(5f, 68f));
                entries.add(new BarEntry(6f, 74f));
                entries.add(new BarEntry(7f, 75f));
                entries.add(new BarEntry(8f, 68f));
                entries.add(new BarEntry(9f, 56f));
                entries.add(new BarEntry(10f, 44f));
                entries.add(new BarEntry(11f, 26f));
        BarDataSet barDataSet = new BarDataSet(entries, "Nb de personnes (unité arbitraire)");
        barDataSet.setColor(getResources().getColor(R.color.colorSecondary));
        barDataSet.setHighLightAlpha(255);
        barDataSet.setHighLightColor(getResources().getColor(R.color.colorAccent));

        BarData data = new BarData(barDataSet);
        data.setBarWidth(0.9f); // set custom bar width
        data.setValueTextSize(20f);
        barChart.setData(data);
        barChart.setFitBars(true); // make the x-axis fit exactly all bars
        barChart.highlightValue(4f, 0);
        barChart.setHighlightPerDragEnabled(false);
        barChart.setHighlightPerTapEnabled(false);
        barChart.invalidate(); // refresh

        // the labels that should be drawn on the XAxis
        final String[] quarters = new String[] { "09h", "10h", "11h", "12h", "13h", "14h", "15h",
                "16h", "17h", "18h", "19h", "20h" };
        IAxisValueFormatter formatter = new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return quarters[(int) value];
            }
        };
        XAxis xAxis = barChart.getXAxis();
        xAxis.setGranularity(1f); // minimum axis-step (interval) is 1
        xAxis.setValueFormatter(formatter);
        xAxis.setYOffset(-5);
        xAxis.setTextSize(20f);
        barChart.getAxisLeft().setTextSize(20f);
        barChart.getAxisRight().setTextSize(20f);
        barChart.getLegend().setTextSize(20f);
        barChart.getLegend().setFormSize(0f);

        barChart.getDescription().setText("");
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onChartFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnChartFragmentInteractionListener) {
            mListener = (OnChartFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnChartFragmentInteractionListener");
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
    public interface OnChartFragmentInteractionListener {
        // TODO: Update argument type and name
        void onChartFragmentInteraction(Uri uri);
    }
}
