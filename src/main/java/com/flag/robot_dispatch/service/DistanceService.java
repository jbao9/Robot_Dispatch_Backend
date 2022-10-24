package com.flag.robot_dispatch.service;

import com.flag.robot_dispatch.model.DispatchCenter;
import com.flag.robot_dispatch.model.Location;
import org.springframework.context.annotation.Bean;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class DistanceService {
    public DistanceService() {
    }

    public double getTotalDistance(DispatchCenter dispatchCenter, Location pickUp, Location delivery) {
        Location vehicleLocation = new Location(new GeoPoint(dispatchCenter.getLat(), dispatchCenter.getLon()));
        return getDistance(vehicleLocation, pickUp)
                + getDistance(pickUp, delivery)
                + getDistance(delivery, vehicleLocation);
    }

    public double getDistance(Location a, Location b) {
        GeoPoint pointA = a.getGeoPoint();
        GeoPoint pointB = b.getGeoPoint();

        // converts from degrees to radians
        double lon1 = Math.toRadians(pointA.getLon());
        double lon2 = Math.toRadians(pointB.getLon());
        double lat1 = Math.toRadians(pointA.getLat());
        double lat2 = Math.toRadians(pointA.getLat());

        // Haversine formula
        double dlon = lon2 - lon1;
        double dlat = lat2 - lat1;
        double x = Math.pow(Math.sin(dlat / 2), 2)
                + Math.cos(lat1) * Math.cos(lat2)
                * Math.pow(Math.sin(dlon / 2), 2);

        double c = 2 * Math.asin(Math.sqrt(x));
        // Radius of earth: 6371 in kilometers/3956 in miles
        double r = 3956;
        // calculate the result
        return (c * r);
    }

}


