package com.example.displayincidentapp;

import java.io.Serializable;
import java.util.Date;

public class TrafficClass implements Serializable {

    private String name, message;
    private double lat, lng;
    private Date date;

    public TrafficClass(String name, String message, double lat, double lng) {
        this.name = name;
        this.message = message;
        this.lat = lat;
        this.lng = lng;
        this.date = new Date();

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }


}
