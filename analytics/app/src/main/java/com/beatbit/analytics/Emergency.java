package com.beatbit.analytics;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Daniel on 8/2/2015.
 */
public class Emergency implements Serializable {
    @com.google.gson.annotations.SerializedName("__createdAt")
    private String date;
    private String id;
    private String patientid;
    private String description;

    public Emergency(String date, String description) {
        this.date = date;
        this.description = description;
    }

    public Emergency() {

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
