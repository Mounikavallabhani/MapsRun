package com.strsar.mapsrun;

public class MarkerModel {
    double latitude ;
    double longutide;

    public MarkerModel(double latitude, double longutide) {
        this.latitude = latitude;
        this.longutide = longutide;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongutide() {
        return longutide;
    }

    public void setLongutide(double longutide) {
        this.longutide = longutide;
    }
}
