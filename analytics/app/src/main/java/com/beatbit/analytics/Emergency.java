package com.beatbit.analytics;

import java.util.List;

/**
 * Created by Daniel on 8/2/2015.
 */
public class Emergency {
    private String date;
    private String description;

    public Emergency(String date, String description) {
        this.date = date;
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
