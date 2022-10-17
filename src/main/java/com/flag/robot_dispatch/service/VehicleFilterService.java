package com.flag.robot_dispatch.service;

import com.flag.robot_dispatch.filter.*;
import com.flag.robot_dispatch.model.Location;
import com.flag.robot_dispatch.model.Status;
import com.flag.robot_dispatch.model.Vehicle;
import com.flag.robot_dispatch.repository.VehicleRepository;
import com.flag.robot_dispatch.util.DistanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class VehicleFilterService {
    private VehicleRepository vehicleRepository;
    private List<Vehicle> availableVehicles;

    @Autowired
    public VehicleFilterService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public List<VehicleFilter> buildFilterList(Location pickupLocation,
                                                Location deliveryLocation,
                                                int timeRequirement,
                                                int volumeRequirement,
                                                int weightRequirement) {
        // TODO
        List<VehicleFilter> vehicleFilterList = new ArrayList<>();
//        vehicleFilterList.add(new VehicleSpeedFilter(timeRequirement, pickupLocation, deliveryLocation));
        vehicleFilterList.add(new VehicleRangeFilter(pickupLocation, deliveryLocation));
        vehicleFilterList.add(new VehicleCapacityFilter(volumeRequirement, weightRequirement));
        return vehicleFilterList;
    }

    public List<Vehicle> filterAvailableVehicles(List<VehicleFilter> vehicleFilterList) {
        // TODO: is this correct??
        VehicleFilter andVehicleFilter = new AndVehicleFilter(vehicleFilterList);
        return andVehicleFilter.checkCondition(findAvailableVehicles());
    }

    // get available vehicles
    public List<Vehicle> findAvailableVehicles() {
        this.availableVehicles = vehicleRepository.findByStatus(Status.available);
        return this.availableVehicles;
    }

}
