package com.flag.robot_dispatch.model;

import org.springframework.data.elasticsearch.core.geo.GeoPoint;

import javax.naming.Name;
import javax.persistence.*;

@Entity
@Table(name = "center")
public class DispatchCenter extends Location{

    @Id
    private Long id;
    private String address;
    private String name;
    private double lon;
    private double lat;

    public DispatchCenter() {}

    public DispatchCenter(Long id, String address, String name, double lon, double lat) {
        this.id = id;
        this.address = address;
        this.name = name;
        this.lon = lon;
        this.lat = lat;

    }

//    public DispatchCenter(GeoPoint geoPoint, Long id, String address, String name) {
//        super(geoPoint);
//        this.id = id;
//        this.address = address;
//        Name = name;
//    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    @Override
    public Long getId() {
        return id;
    }

    public DispatchCenter setId(Long id) {
        this.id = id;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public DispatchCenter setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getName() {
        return name;
    }

    public DispatchCenter setName(String name) {
        this.name = name;
        return this;
    }
}
