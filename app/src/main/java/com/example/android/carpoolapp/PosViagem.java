package com.example.android.carpoolapp;

public class PosViagem {
    private String key;
    private double latitude, longitude;

    public PosViagem() {

    }

    public PosViagem(String key, double latitude, double longitude) {
        this.key = key;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
