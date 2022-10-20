package com.flag.robot_dispatch.model;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

public class Location {
    private Long id;
    private GeoPoint geoPoint;

    public Location() {
    }

    public Location(GeoPoint geoPoint) {
        this.geoPoint = geoPoint;
    }

    public Long getId() {
        return id;
    }

    public GeoPoint getGeoPoint() {
        return geoPoint;
    }
}
