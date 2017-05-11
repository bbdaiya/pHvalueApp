package com.example.bbdaiya.phvalue;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by bbdaiya on 12-May-17.
 */

public class Data implements Parcelable{
    private String value;
    private String date_added;

    public Data(String value, String date_added) {
        this.value = value;
        this.date_added = date_added;
    }

    public Data(Parcel in){
        String[] data = new String[2];

        in.readStringArray(data);
        this.value = data[0];
        this.date_added = data[1];

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]{
                this.value,this.date_added
        });
    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Data createFromParcel(Parcel in) {
            return new Data(in);
        }

        public Data[] newArray(int size) {
            return new Data[size];
        }
    };
}
