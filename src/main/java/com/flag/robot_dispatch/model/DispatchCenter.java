package com.flag.robot_dispatch.model;

import javax.persistence.*;

@Entity
@Table(name = "center")
public class DispatchCenter {

    @Id
    private String id;
    private String address;
    private double lat;
    private double lon;
    // TODO: We need a function to sync the number when add or delete vehicle
//    private int num_heavyDrone;
//    private int num_heavyRobot;
//    private int num_lightDrone;
//    private int num_lightRobot;

    public DispatchCenter() {}

    public DispatchCenter(String id, String address, double lat, double lon) {
        this.id = id;
        this.address = address;
        this.lat = lat;
        this.lon = lon;
    }


}
