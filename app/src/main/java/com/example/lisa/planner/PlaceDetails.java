package com.example.lisa.planner;

public class PlaceDetails {
    private String name;
    private String address;
    private String task;
    private double longitude;
    private double latitude;

    public PlaceDetails() {
    }

    public PlaceDetails(String name, String address /*, String task*/, double longitude, double latitude) {
        this.name = name;
        this.address = address;
        // this.task = task;
        this.longitude = longitude;
        this.latitude = latitude;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setTask(String task) { this.task = task; }

    public String getTask() { return task; }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLongitude() {
        return longitude;
    }

    @Override
    public String toString() {
        return name +
                "," + address +
                //"," + task +
                ","+ longitude +
                "," + latitude + "\n";
    }
}
