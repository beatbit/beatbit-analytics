package com.beatbit.analytics.fragments;


import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.beatbit.analytics.GraphCreator;
import com.beatbit.analytics.R;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

/**
 * A simple {@link Fragment} subclass.
 */
public class HeartRateOverDayFragment extends Fragment {
    private GraphView graph;
    private LineGraphSeries<DataPoint> series;

    public HeartRateOverDayFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(R.layout.fragment_heart_rate_over_day, container, false);

        graph = (GraphView) inflatedView.findViewById(R.id.graph);
        series = new LineGraphSeries<DataPoint>();

        GraphCreator.create(series, getActivity(), graph);


        return inflatedView;
    }


}
