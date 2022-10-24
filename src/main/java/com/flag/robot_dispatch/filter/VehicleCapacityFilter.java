package com.flag.robot_dispatch.filter;

import com.flag.robot_dispatch.model.Vehicle;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

public class VehicleCapacityFilter implements VehicleFilter {
    private int lengthRequirement;
    private int widthRequirement;
    private int heightRequirement;
    private int weightRequirement;

    private static final double CAPACITY_RATIO = 1.2;

    public VehicleCapacityFilter(int lengthRequirement,
                                 int widthRequirement,
                                 int heightRequirement,
                                 int weightRequirement) {

        this.lengthRequirement = lengthRequirement;
        this.widthRequirement = widthRequirement;
        this.heightRequirement = heightRequirement;
        this.weightRequirement = weightRequirement;
    }

    @Override
    public List<Vehicle> checkCondition(List<Vehicle> availableVehicles) {
        List<Vehicle> filteredAvailableVehicles = new ArrayList<>();
        for (Vehicle vehicle : availableVehicles) {
            if (vehicle.getType().getLength_capacity() >= lengthRequirement * CAPACITY_RATIO &&
                    vehicle.getType().getWidth_capacity() >= widthRequirement * CAPACITY_RATIO &&
                    vehicle.getType().getHeight_capacity() >= heightRequirement * CAPACITY_RATIO &&
                    vehicle.getType().getWeight_capacity() >= weightRequirement * CAPACITY_RATIO) {
                filteredAvailableVehicles.add(vehicle);
            }
        }
        return filteredAvailableVehicles;
    }
}
