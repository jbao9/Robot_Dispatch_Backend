package com.flag.robot_dispatch.filter;

import com.flag.robot_dispatch.exception.InvalidInputException;
import com.flag.robot_dispatch.model.Location;
import com.flag.robot_dispatch.model.Vehicle;
import com.flag.robot_dispatch.util.DistanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

// TODO : NOT WORKING NOW
public class VehicleSpeedFilter implements VehicleFilter{

    private int timeRequirement;
    private Location pickupLocation;
    private Location deliveryLocation;

    private DistanceService distanceService = new DistanceService();


    public VehicleSpeedFilter(int timeRequirement, Location pickupLocation, Location deliveryLocation) {
        this.timeRequirement = timeRequirement;
        this.pickupLocation = pickupLocation;
        this.deliveryLocation = deliveryLocation;
    }

    @Override
    public List<Vehicle> checkCondition(List<Vehicle> availableVehicles) {
        if (timeRequirement == 0) {
            throw new InvalidInputException("Invalid Delivery Time");
        }

        List<Vehicle> filteredAvailableVehicles = new ArrayList<>();
        for (Vehicle vehicle : availableVehicles) {
            double totalDistance = distanceService.getTotalDistance(vehicle.getLocation(), pickupLocation, deliveryLocation);
            if (vehicle.getType().getSpeed() >= totalDistance / timeRequirement) {
                filteredAvailableVehicles.add(vehicle);
            }
        }
        return filteredAvailableVehicles;
    }

}
