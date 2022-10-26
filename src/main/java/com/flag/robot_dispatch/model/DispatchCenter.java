package com.flag.robot_dispatch.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

import javax.naming.Name;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "center")
public class DispatchCenter extends Location{

    @Id
    private Long id;
    private String address;
    private String name;
    private double lon;
    private double lat;


    @OneToMany(mappedBy = "center", fetch = FetchType.LAZY)     //new added
    private List<Order> orders;                                    //new added

    public DispatchCenter() {}

//    public DispatchCenter(Long id, String address, String name, double lon, double lat) {
//        this.id = id;
//        this.address = address;
//        this.name = name;
//        this.lon = lon;
//        this.lat = lat;
//
//    }

    public DispatchCenter(Builder builder) {
        this.id = builder.id;
        this.address = builder.address;
        this.name = builder.name;
        this.lon = builder.lon;
        this.lat = builder.lat;
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

    public static class Builder {
        @JsonProperty("id")
        private Long id;

        @JsonProperty("address")
        private String address;

        @JsonProperty("name")
        private String name;

        @JsonProperty("lon")
        private double lon;

        @JsonProperty("lat")
        private double lat;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setAddress(String address) {
            this.address = address;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setLon(double lon) {
            this.lon = lon;
            return this;
        }

        public Builder setLat(double lat) {
            this.lat = lat;
            return this;
        }

        public DispatchCenter build() {
            return new DispatchCenter(this);
        }
    }
}
