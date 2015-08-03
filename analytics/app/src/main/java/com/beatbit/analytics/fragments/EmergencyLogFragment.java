package com.beatbit.analytics.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.beatbit.analytics.AzureClient;
import com.beatbit.analytics.Emergency;
import com.beatbit.analytics.R;
import com.beatbit.analytics.activities.PatientMonitorActivity;

import java.util.ArrayList;
import java.util.List;

public class EmergencyLogFragment extends Fragment {
    private static final String TAG = "activities";

    private List<Emergency> emergencies;

    public EmergencyLogFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View inflatedView = inflater.inflate(R.layout.fragment_emergency_log, container, false);

        try {
            new AzureClient(getActivity()).getEmergencies(((PatientMonitorActivity) getActivity()).getPatient(), new AzureClient.AzureClientListener() {
                @Override
                public void onLoaded(Object obj) {
                    emergencies = (List<Emergency>) obj;

                    ListView lv = (ListView) inflatedView.findViewById(R.id.lv_emergencies);
                    lv.setAdapter(new EmergencyLogAdapter());

                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            DisplayEmergencyFragment frag = new DisplayEmergencyFragment();

                            Bundle args = new Bundle();
                            args.putSerializable("emergency", emergencies.get(position));

                            frag.setArguments(args);

                            ((PatientMonitorActivity) getActivity()).setContent(frag);
                        }
                    });
                }
            });
        } catch(Exception e) {
            Log.e(TAG, Log.getStackTraceString(e));
        }

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
