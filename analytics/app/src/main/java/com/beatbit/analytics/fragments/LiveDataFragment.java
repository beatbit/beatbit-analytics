package com.beatbit.analytics.fragments;


import android.content.BroadcastReceiver;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beatbit.analytics.GraphCreator;
import com.beatbit.analytics.HeartBeatListener;
import com.beatbit.analytics.HeartRate;
import com.beatbit.analytics.R;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class LiveDataFragment extends Fragment implements HeartBeatListener {
    private GraphView graph;
    private LineGraphSeries<DataPoint> series;
    private RelativeLayout bpmLayout;
    private TextView bpmTxtv;
    private Animation fadeOut;
    private Animation fadeIn;
    private List<HeartRate> heartRates;
    private BroadcastReceiver broadcastReceiver;

    public LiveDataFragment() {

    }

    @Override
    public void onHeartBeat(HeartRate heartRate) {
        heartRates.add(heartRate);

        // Play the animation
        heartBeatAnimation(heartRate);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(R.layout.fragment_live_data, container, false);

        bpmLayout = (RelativeLayout) inflatedView.findViewById(R.id.rl_bpmLayout);
        bpmTxtv = (TextView) inflatedView.findViewById(R.id.txtv_hr);

        graph = (GraphView) inflatedView.findViewById(R.id.graph);

        heartRates = new ArrayList<HeartRate>();

        DataPoint[] data = {
                new DataPoint(0, 40),
                new DataPoint(1, 41),
                new DataPoint(3, 41),
                new DataPoint(10, 42),
                new DataPoint(50, 55),
                new DataPoint(75, 65),
                new DataPoint(90, 60),
                new DataPoint(100, 57)
        };

        series = new LineGraphSeries<DataPoint>(data);
        
        // Create/style the graph to our standards
        GraphCreator.create(series, getActivity(), graph);


        for(DataPoint point : data) {
            heartRates.add(new HeartRate((int)point.getY(), (long)point.getX()));
        }

        onHeartBeat(new HeartRate(40, 200));

        return inflatedView;
    }

    private void heartBeatAnimation(final HeartRate heartRate) {
        // Create a fadeout animation
        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setDuration(300);

        bpmLayout.setAnimation(fadeOut);

        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                bpmTxtv.setText(heartRate.getValue() + "");

                series.appendData(new DataPoint(heartRate.getTime(), heartRate.getValue()), true, 40);

                Animation fadeIn = new AlphaAnimation(0, 1);
                fadeIn.setDuration(300);
                bpmLayout.setAnimation(fadeIn);

                fadeIn.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        onHeartBeat(new HeartRate(heartRate.getValue() + 1, heartRate.getTime() + 1));
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

                fadeIn.start();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        fadeOut.start();
    }

}
