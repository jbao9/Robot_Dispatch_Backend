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
    private String Name;

    public DispatchCenter() {}

    public DispatchCenter(Long id, String address, String name) {
        this.id = id;
        this.address = address;
        Name = name;
    }

    public DispatchCenter(GeoPoint geoPoint, Long id, String address, String name) {
        super(geoPoint);
        this.id = id;
        this.address = address;
        Name = name;
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
        return Name;
    }

    public DispatchCenter setName(String name) {
        Name = name;
        return this;
    }
}
