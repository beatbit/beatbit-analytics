package com.beatbit.analytics.fragments;


import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.beatbit.analytics.AzureClient;
import com.beatbit.analytics.Emergency;
import com.beatbit.analytics.GraphCreator;
import com.beatbit.analytics.R;
import com.beatbit.analytics.activities.PatientMonitorActivity;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

/**
 * A simple {@link Fragment} subclass.
 */
public class DisplayEmergencyFragment extends Fragment {
    private LineGraphSeries<DataPoint> series;
    private Emergency emergency;

    public DisplayEmergencyFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        emergency = (Emergency) getArguments().getSerializable("emergency");

        View inflatedView = inflater.inflate(R.layout.fragment_display_emergency, container, false);

        GraphView graph = (GraphView) inflatedView.findViewById(R.id.graph);
        series = new LineGraphSeries<DataPoint>();

        GraphCreator.create(series, getActivity(), graph);

        Button description = (Button) inflatedView.findViewById(R.id.btn_description);
        description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDescriptionDialog();
            }
        });

        return inflatedView;
    }

    private void showDescriptionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(emergency.getDate());
        builder.setMessage(emergency.getDescription());
        builder.show();
    }

}
