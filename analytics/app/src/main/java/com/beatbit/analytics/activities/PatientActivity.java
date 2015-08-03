package com.beatbit.analytics.activities;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.beatbit.analytics.AnalyticsApp;
import com.beatbit.analytics.AzureClient;
import com.beatbit.analytics.Constants;
import com.beatbit.analytics.MonitorService;
import com.beatbit.analytics.Patient;
import com.beatbit.analytics.R;

import java.util.List;


public class PatientActivity extends ActionBarActivity {
    private static final String TAG = "activities";
    private PatientAdapter adapter;
    private List<Patient> patients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);


        try {
            AzureClient c = new AzureClient(this);

            // Read all patients from azure
            new AzureClient(this).getPatients(new AzureClient.AzureClientListener() {
                @Override
                public void onLoaded(final Object object) {
                    PatientActivity.this.patients = (List<Patient>) object;

                    ((AnalyticsApp) getApplication()).setPatients(patients);

                    ListView patientListView = (ListView) findViewById(R.id.lv_patients);
                    patientListView.setAdapter(adapter = new PatientAdapter(PatientActivity.this));

                    patientListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            // Start the monitoring activity
                            Intent intent = new Intent(PatientActivity.this, PatientMonitorActivity.class);
                            intent.putExtra(Constants.PATIENT, patients.get(position));
                            startActivity(intent);
                        }
                    });
                }
            });

        } catch(Exception e) {
            Log.e(TAG, Log.getStackTraceString(e));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_patient, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    private class PatientAdapter extends ArrayAdapter<Patient> {

        public PatientAdapter(Context context) {
            super(context, -1, patients);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View inflatedView = convertView;

            if(inflatedView == null) {
                inflatedView = LayoutInflater.from(PatientActivity.this).inflate(R.layout.patient_row, parent, false);
            }

            ((TextView) inflatedView.findViewById(R.id.txtv_patientName)).setText(patients.get(position).getName());

            return inflatedView;
        }
    }
}
