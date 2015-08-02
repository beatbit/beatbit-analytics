package com.beatbit.analytics.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.beatbit.analytics.Patient;
import com.beatbit.analytics.R;

import java.util.ArrayList;
import java.util.List;


public class PatientActivity extends ActionBarActivity {
    private PatientAdapter adapter;
    private List<Patient> patients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);

        patients = new ArrayList<Patient>();

        patients.add(new Patient("Rahul", 90));
        patients.add(new Patient("Daniel", 44));

        ListView patientListView = (ListView) findViewById(R.id.lv_patients);
        patientListView.setAdapter(adapter = new PatientAdapter(this));

        patientListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Start the monitoring activity
                Intent intent = new Intent(PatientActivity.this, MonitorActivity.class);
                intent.putExtra("patient", patients.get(position));
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_patient, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

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
            ((TextView) inflatedView.findViewById(R.id.txtv_bpm)).setText(patients.get(position).getHeartrate() + " bpm");

            return inflatedView;
        }
    }
}
