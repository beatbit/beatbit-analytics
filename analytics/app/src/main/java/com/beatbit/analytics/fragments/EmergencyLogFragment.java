package com.beatbit.analytics.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.beatbit.analytics.Emergency;
import com.beatbit.analytics.R;

import java.util.ArrayList;
import java.util.List;

public class EmergencyLogFragment extends Fragment {
    private List<Emergency> emergencies;

    public EmergencyLogFragment() {
        emergencies = new ArrayList<Emergency>();
        emergencies.add(new Emergency("8/1/2015", "Heart failed"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(R.layout.fragment_emergency_log, container, false);

        ListView lv = (ListView) inflatedView.findViewById(R.id.lv_emergencies);
        lv.setAdapter(new EmergencyLogAdapter());

        return inflatedView;
    }

    private class EmergencyLogAdapter extends ArrayAdapter<Emergency> {

        public EmergencyLogAdapter() {
            super(getActivity(), -1, emergencies);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View inflatedView = convertView;

            if(inflatedView == null) {
                inflatedView = LayoutInflater.from(getContext()).inflate(R.layout.emergency_log_row, parent, false);
            }

            ((TextView) inflatedView.findViewById(R.id.txtv_emergencyDate)).setText(emergencies.get(position).getDate());

            return inflatedView;
        }
    }
}
