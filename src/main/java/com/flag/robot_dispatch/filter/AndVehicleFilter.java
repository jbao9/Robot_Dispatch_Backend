package com.flag.robot_dispatch.filter;

import com.flag.robot_dispatch.model.Vehicle;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class AndVehicleFilter implements VehicleFilter{

    private List<VehicleFilter> VehicleFilterList;

    public AndVehicleFilter(List<VehicleFilter> VehicleFilterList) {
        this.VehicleFilterList = VehicleFilterList;
    }

    @Override
    public List<Vehicle> checkCondition(List<Vehicle> availableVehicles) {
        List<Vehicle> filteredAvailableVehicles = availableVehicles;
        for (VehicleFilter vehicleFilter: VehicleFilterList) {
            filteredAvailableVehicles = vehicleFilter.checkCondition(filteredAvailableVehicles);
        }
        return filteredAvailableVehicles;
    }
}
