package com.flag.robot_dispatch.filter;

import com.flag.robot_dispatch.model.Vehicle;

import java.util.List;

public interface VehicleFilter {
    public List<Vehicle> checkCondition(List<Vehicle> availableVehicles);
}
