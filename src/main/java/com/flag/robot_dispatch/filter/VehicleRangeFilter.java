package com.flag.robot_dispatch.filter;

import com.flag.robot_dispatch.model.Location;
import com.flag.robot_dispatch.model.Vehicle;
import com.flag.robot_dispatch.service.DistanceService;

import java.util.ArrayList;
import java.util.List;

public class VehicleRangeFilter implements VehicleFilter {

    private Location pickupLocation;
    private Location deliveryLocation;
    private DistanceService distanceService = new DistanceService();

    private static final double RANGE_RATIO = 1.5;

    public VehicleRangeFilter(Location pickupLocation, Location deliveryLocation) {
        this.pickupLocation = pickupLocation;
        this.deliveryLocation = deliveryLocation;
    }

    @Override
    public List<Vehicle> checkCondition(List<Vehicle> availableVehicles) {
        List<Vehicle> filteredVehicles = new ArrayList<>();

        for (Vehicle vehicle : availableVehicles) {
            double totalDistance = distanceService.getTotalDistance(vehicle.getLocation(), pickupLocation, deliveryLocation);
            if (vehicle.getType().getRange() >= totalDistance * RANGE_RATIO) {
                filteredVehicles.add(vehicle);
            }
        }
        return filteredVehicles;
    }
}
