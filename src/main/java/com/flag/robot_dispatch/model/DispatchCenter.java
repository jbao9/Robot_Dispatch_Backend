package com.flag.robot_dispatch.model;

public class DispatchCenter {
    private String address;
    private double lat;
    private double lon;

    public DispatchCenter(String address, double lat, double lon) {
        this.address = address;
        this.lat = lat;
        this.lon = lon;
    }
}
