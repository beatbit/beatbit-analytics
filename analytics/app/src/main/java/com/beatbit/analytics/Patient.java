package com.beatbit.analytics;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Daniel on 8/1/2015.
 */
public class Patient implements Serializable {
    private String name;
    private int heartrate;

    public Patient(String name, int heartrate) {
        this.name = name;
        this.heartrate = heartrate;
    }

    public int getHeartrate() {
        return heartrate;
    }

    public void setHeartrate(int heartrate) {
        this.heartrate = heartrate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
