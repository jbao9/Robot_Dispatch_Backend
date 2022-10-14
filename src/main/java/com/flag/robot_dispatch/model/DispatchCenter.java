package com.flag.robot_dispatch.model;

import javax.persistence.*;

@Entity
@Table(name = "center")
public class DispatchCenter {

    @Id
    private Long id;
    private String address;
    private double lat;
    private double lon;

    public DispatchCenter() {}

    public DispatchCenter(Long id, String address, double lat, double lon) {
        this.id = id;
        this.address = address;
        this.lat = lat;
        this.lon = lon;
    }


}
