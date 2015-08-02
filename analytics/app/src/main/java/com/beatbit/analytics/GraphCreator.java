package com.beatbit.analytics;

import android.content.Context;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.Series;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Daniel on 8/2/2015.
 */
public class GraphCreator {

    /**
     * Styles the graph
     */
    public static void create(LineGraphSeries<DataPoint> series, Context context, GraphView graph) {
        int white = context.getResources().getColor(android.R.color.white);

        graph.getGridLabelRenderer().setHorizontalAxisTitle("Time");
        graph.getGridLabelRenderer().setVerticalAxisTitle("Heart Rate");

        series.setColor(context.getResources().getColor(R.color.colorPrimary));

        GridLabelRenderer renderer = graph.getGridLabelRenderer();

        renderer.setHorizontalLabelsColor(white);
        renderer.setVerticalLabelsColor(white);
        renderer.setVerticalAxisTitleColor(white);
        renderer.setVerticalLabelsColor(white);
        renderer.setHorizontalAxisTitleColor(white);
        renderer.setGridColor(white);
        renderer.setPadding(15);

        graph.addSeries(series);
    }
}
