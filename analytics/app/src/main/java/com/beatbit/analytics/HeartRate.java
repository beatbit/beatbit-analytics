package com.beatbit.analytics;

import java.io.Serializable;

/**
 * Created by Daniel on 8/1/2015.
 */
public class HeartRate {
    private int value;
    private long time;

    public HeartRate() {

    }

    public HeartRate(int value, long time) {
        this.value = value;
        this.time = time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public long getTime() {
        return time;
    }
}
