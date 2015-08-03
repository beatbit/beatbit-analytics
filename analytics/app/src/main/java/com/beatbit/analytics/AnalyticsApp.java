package com.beatbit.analytics;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel on 8/2/2015.
 */
public class AnalyticsApp extends Application {
    private List<Patient> patients;

    @Override
    public void onCreate() {
        super.onCreate();
        patients = new ArrayList<Patient>();
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }
}
