package com.beatbit.analytics;

/**
 * Created by Daniel on 8/2/2015.
 */
public class AverageHeartRate {
    private String date;
    private int rate;

    public AverageHeartRate(String date, int rate) {
        this.date = date;
        this.rate = rate;
    }

    public int getRate() {
        return rate;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getDate() {
        return date;
    }
}
