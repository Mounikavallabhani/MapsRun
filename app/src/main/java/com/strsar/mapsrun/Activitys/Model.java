package com.strsar.mapsrun.Activitys;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Model implements Serializable {


    String name;
    String data;

    public Model(String name, String data) {
        this.name = name;
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}