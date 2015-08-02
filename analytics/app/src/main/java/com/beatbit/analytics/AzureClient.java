package com.beatbit.analytics;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.table.TableOperationCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel on 8/2/2015.
 */
public class AzureClient {
    private static final String TAG = "analytics";
    private MobileServiceClient azureClient;
    private MobileServiceTable<Emergency> emergencyTable;
    private MobileServiceTable<Patient> patientTable;

    public AzureClient(Context context) throws Exception {
        azureClient = new MobileServiceClient(
                "https://beatbit.azure-mobile.net/",
                "RqIjTJBDLvuoMcTlsTBDtWkqTREmcJ72",
                context);

        List<Emergency> ems = new ArrayList<Emergency>();
        ems.add(new Emergency("5/18/15", "a bad one"));

        patientTable = azureClient.getTable(Patient.class);
        emergencyTable = azureClient.getTable(Emergency.class);
    }

    public void updateEmergency(Emergency emergency) {
        emergencyTable.update(emergency);
    }

    public void addEmergency(Emergency emergency) {
        emergencyTable.insert(emergency);
    }

    public void updatePatient(Patient patient) {
        patientTable.update(patient);
    }

    public void getEmergencies(final Patient patient, final AzureClientListener listener) throws Exception {

        new AsyncTask<Void, Void, List<Emergency>>() {

            @Override
            protected List<Emergency> doInBackground(Void... params) {
                List<Emergency> emergencies = null;

                try {
                    emergencies = emergencyTable.where().field("patientid").eq(patient.getId()).execute().get();
                } catch(Exception e) {
                    Log.e(TAG, Log.getStackTraceString(e));
                }

                return emergencies;
            }

            @Override
            protected void onPostExecute(List<Emergency> emergencies) {
                super.onPostExecute(emergencies);

                listener.onLoaded(emergencies);
            }
        }.execute();
    }

    public void getPatients(final AzureClientListener listener) throws Exception {
        new AsyncTask<Void, Void, List<Patient>>() {

            @Override
            protected List<Patient> doInBackground(Void... params) {
                List<Patient> patients = new ArrayList<Patient>();

                try {
                    patients = patientTable.execute().get();
                } catch(Exception e) {
                    Log.e(TAG, Log.getStackTraceString(e));
                }

                return patients;
            }

            @Override
            protected void onPostExecute(List<Patient> patients) {
                super.onPostExecute(patients);
                listener.onLoaded(patients);
            }
        }.execute();
    }

    public void addPatient(Patient patient) {
        patientTable.insert(patient, new TableOperationCallback<Patient>() {
            @Override
            public void onCompleted(Patient entity, Exception exception, ServiceFilterResponse response) {
                if (exception == null) {
                    Log.d(TAG, "worked");
                } else {
                    Log.e(TAG, Log.getStackTraceString(exception));
                }
            }
        });
    }

    public static interface AzureClientListener {
        void onLoaded(Object obj);
    }
}
