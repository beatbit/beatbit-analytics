package com.beatbit.analytics;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.GsonBuilder;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceException;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.table.TableOperationCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel on 8/2/2015.
 */
public class AzureClient {
    private static final String TAG = "analytics";
    private MobileServiceClient azureClient;

    public AzureClient(Context context) throws Exception {
        azureClient = new MobileServiceClient(
                "https://beatbit.azure-mobile.net/",
                "RqIjTJBDLvuoMcTlsTBDtWkqTREmcJ72",
                context);
    }

    public void getPatients(final AzureClientListener listener) throws Exception {
        new AsyncTask<Void, Void, List<Patient>>() {

            @Override
            protected List<Patient> doInBackground(Void... params) {
                List<Patient> patients = new ArrayList<Patient>();

                try {
                    patients = azureClient.getTable(Patient.class).execute().get();
                } catch(Exception e) {
                    Log.e(TAG, Log.getStackTraceString(e));
                }

                return patients;
            }

            @Override
            protected void onPostExecute(List<Patient> patients) {
                super.onPostExecute(patients);
                listener.onPatientsLoaded(patients);
            }
        }.execute();
    }

    public void addPatient(Patient patient) {
        azureClient.getTable(Patient.class).insert(patient, new TableOperationCallback<Patient>() {
            @Override
            public void onCompleted(Patient entity, Exception exception, ServiceFilterResponse response) {
                if(exception == null) {
                    Log.d(TAG, "worked");
                }
                else {
                    Log.e(TAG, Log.getStackTraceString(exception));
                }
            }
        });
    }

    public static interface AzureClientListener {
        void onPatientsLoaded(List<Patient> patients);
    }
}
