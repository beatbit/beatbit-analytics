package com.beatbit.analytics;

import android.util.Log;

import com.google.gson.JsonObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.json.*;

/**
 * Created by Daniel on 8/2/2015.
 */
public class Emergency implements Serializable {
    @com.google.gson.annotations.SerializedName("__createdAt")
    private String date;
    private String id;
    private String patientid;
    private String description;

    @com.google.gson.annotations.SerializedName("heartrates")
    private String heartRateJson;

    public Emergency(String date, String description) {
        this.date = date;
        this.description = description;
    }

    public Emergency() {

    }

    public void setHeartRateJson(String heartRateJson) {
        this.heartRateJson = heartRateJson;
    }

    public String getHeartRateJson() {
        return heartRateJson;
    }

    public List<HeartRate> getHeartRates() {
        List<HeartRate> heartRates = new ArrayList<HeartRate>();

        try {
            JSONArray a = new JSONArray(heartRateJson);

            int len = a.length();
            for(int i = 0; i < len; i++) {
                JSONObject o = a.getJSONObject(i);

                HeartRate h = new HeartRate();
                h.setTime(o.getLong("time"));
                h.setValue(o.getInt("heartrate"));

                heartRates.add(h);
            }
        } catch(Exception e) {
            Log.e("com.beatbit.analytics", Log.getStackTraceString(e));
        }

        return heartRates;

    }

    public String getPatientid() {
        return patientid;
    }

    public void setPatientid(String patientid) {
        this.patientid = patientid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
