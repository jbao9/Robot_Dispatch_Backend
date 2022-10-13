package com.flag.robot_dispatch.util;

import com.flag.robot_dispatch.model.Location;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

public class DistanceUtil {

    public DistanceUtil() {
    }

    public double getDistance(Location a, Location b) {
        GeoPoint pointA = a.getGeoPoint();
        GeoPoint pointB = b.getGeoPoint();
        return Math.sqrt(
                Math.pow((pointA.getLat() - pointB.getLat()), 2) +
                        Math.pow((pointA.getLon() - pointB.getLon()), 2)
        );
    }
}
