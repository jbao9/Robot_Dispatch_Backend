package com.flag.robot_dispatch.filter;

import com.flag.robot_dispatch.exception.InvalidInputException;
import com.flag.robot_dispatch.model.Location;
import com.flag.robot_dispatch.model.Vehicle;
import com.flag.robot_dispatch.service.DistanceService;

import java.util.ArrayList;
import java.util.List;

public class VehicleSpeedFilter implements VehicleFilter {
    // timeRequirement unit is hour
    private double timeRequirement;
    private Location pickupLocation;
    private Location deliveryLocation;
    private DistanceService distanceService = new DistanceService();

    private static final double SPEED_RATIO = 1.5;


    public VehicleSpeedFilter(double timeRequirement, Location pickupLocation, Location deliveryLocation) {
        this.timeRequirement = timeRequirement;
        this.pickupLocation = pickupLocation;
        this.deliveryLocation = deliveryLocation;
    }

    @Override
    public List<Vehicle> checkCondition(List<Vehicle> availableVehicles) {

        List<Vehicle> filteredAvailableVehicles = new ArrayList<>();
        for (Vehicle vehicle : availableVehicles) {
            double totalDistance = distanceService.getTotalDistance(vehicle.getLocation(), pickupLocation, deliveryLocation);
            if (vehicle.getType().getSpeed() >= (totalDistance / timeRequirement) * SPEED_RATIO) {
                filteredAvailableVehicles.add(vehicle);
            }
        }
        return filteredAvailableVehicles;
    }

}
