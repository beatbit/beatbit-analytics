package com.beatbit.analytics.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.beatbit.analytics.AverageHeartRate;
import com.beatbit.analytics.R;
import com.beatbit.analytics.activities.PatientMonitorActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AverageRatesFragment extends Fragment {
    private List<AverageHeartRate> averageHeartRates;

    public AverageRatesFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        averageHeartRates = new ArrayList<AverageHeartRate>();

        averageHeartRates.add(new AverageHeartRate("5/17/15", 66));
        averageHeartRates.add(new AverageHeartRate("6/5/15", 54));

        View inflatedView = inflater.inflate(R.layout.fragment_average_rates, container, false);

        ListView lv = ((ListView) inflatedView.findViewById(R.id.lv_average_rates));
        lv.setAdapter(new AverageRateAdapter());
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Transition to the graph fragment
                ((PatientMonitorActivity) getActivity()).setContent(new HeartRateOverDayFragment());
            }
        });

        return inflatedView;
    }

    private class AverageRateAdapter extends ArrayAdapter<AverageHeartRate> {

        public AverageRateAdapter() {
            super(AverageRatesFragment.this.getActivity(), -1, averageHeartRates);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View inflatedView = convertView;

            if(inflatedView == null) {
                inflatedView = LayoutInflater.from(getContext()).inflate(R.layout.average_rates_row, parent, false);
            }

            ((TextView) inflatedView.findViewById(R.id.txtv_date)).setText(averageHeartRates.get(position).getDate());
            ((TextView) inflatedView.findViewById(R.id.txtv_average_hr)).setText(averageHeartRates.get(position).getRate() + " average bpm");

            return inflatedView;
        }
    }

}
