package com.example.bbdaiya.phvalue;

/**
 * Created by bbdaiya on 12-May-17.
 */

public class Data {
    private String value;
    private String date_added;

    public Data(String value, String date_added) {
        this.value = value;
        this.date_added = date_added;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDate_added() {
        return date_added;
    }

    public void setDate_added(String date_added) {
        this.date_added = date_added;
    }
}
