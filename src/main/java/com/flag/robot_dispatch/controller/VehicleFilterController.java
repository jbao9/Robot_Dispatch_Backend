package com.flag.robot_dispatch.controller;

import com.flag.robot_dispatch.filter.VehicleFilter;
import com.flag.robot_dispatch.model.Location;
import com.flag.robot_dispatch.model.Vehicle;
import com.flag.robot_dispatch.service.GeoCodingService;
import com.flag.robot_dispatch.service.TimeRequirementService;
import com.flag.robot_dispatch.service.VehicleFilterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class VehicleFilterController {
    private VehicleFilterService vehicleFilterService;
    private GeoCodingService geoCodingService;
    private TimeRequirementService timeRequirementService;

    @Autowired
    public VehicleFilterController(VehicleFilterService vehicleFilterService,
                                   GeoCodingService geoCodingService,
                                   TimeRequirementService timeRequirementService) {
        this.vehicleFilterService = vehicleFilterService;
        this.geoCodingService = geoCodingService;
        this.timeRequirementService = timeRequirementService;
    }

    @GetMapping("/available_vehicles")
    public List<Vehicle> getFilteredVehicles(
            @RequestParam(name = "pickup_address") String pickupAddress,
            @RequestParam(name = "delivery_address") String deliveryAddress,
            @RequestParam(name = "pickup_time") String pickupTime,
            @RequestParam(name = "delivery_time") String deliveryTime,
            @RequestParam(name = "delivery_volume") int volumeRequirement,
            @RequestParam(name = "delivery_weight") int weightRequirement) {

        Location pickupLocation = geoCodingService.getLatLng(pickupAddress);
        Location deliveryLocation = geoCodingService.getLatLng(deliveryAddress);
        double timeRequirement = timeRequirementService.getTimeRequirement(pickupTime, deliveryTime);

        // Build the vehicleFilterList
        List<VehicleFilter> vehicleFilterList =
                vehicleFilterService.buildFilterList(
                        pickupLocation,
                        deliveryLocation,
                        timeRequirement,
                        volumeRequirement,
                        weightRequirement);

        // filter all the available vehicles
        return vehicleFilterService.filterAvailableVehicles(vehicleFilterList);
    }

}
