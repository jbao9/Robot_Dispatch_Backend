package com.flag.robot_dispatch.filter;

import com.flag.robot_dispatch.model.Vehicle;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

public class VehicleCapacityFilter implements VehicleFilter {
    private int volumeRequirement;
    private int weightRequirement;

    public VehicleCapacityFilter(int volumeRequirement, int weightRequirement) {
        this.volumeRequirement = volumeRequirement;
        this.weightRequirement = weightRequirement;
    }

    @Override
    public List<Vehicle> checkCondition(List<Vehicle> availableVehicles) {
        List<Vehicle> filteredAvailableVehicles = new ArrayList<>();
        for (Vehicle vehicle : availableVehicles) {
            if (vehicle.getType().getVolume_capacity() >= volumeRequirement &&
                    vehicle.getType().getWeight_capacity() >= weightRequirement) {
                filteredAvailableVehicles.add(vehicle);
            }
        }
        return filteredAvailableVehicles;
    }
}
