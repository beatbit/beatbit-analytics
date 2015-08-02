package com.beatbit.analytics.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.PathInterpolator;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beatbit.analytics.HeartBeatListener;
import com.beatbit.analytics.HeartRate;
import com.beatbit.analytics.R;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.LinkedList;
import java.util.List;


public class MonitorActivity extends ActionBarActivity implements HeartBeatListener {
    private GraphView graph;
    private LineGraphSeries<DataPoint> series;
    private RelativeLayout bpmLayout;
    private TextView bpmTxtv;
    private Animation fadeOut;
    private Animation fadeIn;
    private List<HeartRate> heartRates;
    private BroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor);

        bpmLayout = (RelativeLayout) findViewById(R.id.rl_bpmLayout);
        bpmTxtv = (TextView) findViewById(R.id.txtv_hr);

        graph = (GraphView) findViewById(R.id.graph);

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

        heartRates = new LinkedList<HeartRate>();

        int white = getResources().getColor(android.R.color.white);

        graph.getGridLabelRenderer().setHorizontalAxisTitle("Time");
        graph.getGridLabelRenderer().setVerticalAxisTitle("Heart Rate");

        series = new LineGraphSeries<DataPoint>(data);

        series.setColor(getResources().getColor(R.color.colorPrimary));

        GridLabelRenderer renderer = graph.getGridLabelRenderer();

        renderer.setHorizontalLabelsColor(white);
        renderer.setVerticalLabelsColor(white);
        renderer.setVerticalAxisTitleColor(white);
        renderer.setVerticalLabelsColor(white);
        renderer.setHorizontalAxisTitleColor(white);
        renderer.setGridColor(white);
        renderer.setPadding(15);


        graph.addSeries(series);

        for(DataPoint point : data) {
            heartRates.add(new HeartRate((int)point.getY(), (long)point.getX()));
        }

        onHeartBeat(new HeartRate(40, 200));
    }

    @Override
    public void onHeartBeat(HeartRate heartRate) {
        heartRates.add(heartRate);

        // Play the animation
        heartBeatAnimation(heartRate);
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
