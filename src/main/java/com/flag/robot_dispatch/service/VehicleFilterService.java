package com.flag.robot_dispatch.service;

import com.flag.robot_dispatch.filter.*;
import com.flag.robot_dispatch.model.*;
import com.flag.robot_dispatch.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class VehicleFilterService {
    private final VehicleRepository vehicleRepository;
    private List<Vehicle> availableVehicles;

    @Autowired
    public VehicleFilterService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public List<VehicleFilter> buildFilterList(Location pickupLocation,
                                               Location deliveryLocation,
                                               double timeRequirement,
                                               int lengthRequirement,
                                               int widthRequirement,
                                               int heightRequirement,
                                               int weightRequirement) {

        List<VehicleFilter> vehicleFilterList = new ArrayList<>();
        vehicleFilterList.add(new VehicleSpeedFilter(timeRequirement, pickupLocation, deliveryLocation));
        vehicleFilterList.add(new VehicleRangeFilter(pickupLocation, deliveryLocation));
        vehicleFilterList.add(new VehicleCapacityFilter(lengthRequirement, widthRequirement, heightRequirement, weightRequirement));
        return vehicleFilterList;
    }

    public List<Vehicle> filterAvailableVehicles(List<VehicleFilter> vehicleFilterList) {
        availableVehicles = vehicleRepository.findByStatus(Status.available);

        /** hard code available vehicle list for testing
        Long numLong = 3L;
        availableVehicles = new ArrayList<>();
        for (int i = 10; i < 100; i++) {
            availableVehicles.add(
                    new Vehicle.Builder()
                            .setName("Vehicle No." + i).
                            setStatus(Status.available).
                            setType(new VehicleType.Builder().
                                    setType(MachineType.Drone_Heavy).
                                    setLength_capacity(i).
                                    setWidth_capacity(i).
                                    setHeight_capacity(i).
                                    setWeight_capacity(i).
                                    setSpeed(i).
                                    setRange(i).
                                    build()).
                            setLocation(new DispatchCenter(
                                    numLong,
                                    "3995 Alemany Blvd, San Francisco, CA 94132",
                                    "Dispatch Center 3",
                                    -122.4679266,
                                    37.7102661)).
                            build());
        }
         */

        VehicleFilter andVehicleFilter = new AndVehicleFilter(vehicleFilterList);
        return andVehicleFilter.checkCondition(availableVehicles);
    }

}
