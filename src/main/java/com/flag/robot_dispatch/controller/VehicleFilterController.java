package com.flag.robot_dispatch.controller;

import com.flag.robot_dispatch.filter.VehicleFilter;
import com.flag.robot_dispatch.model.Location;
import com.flag.robot_dispatch.model.Vehicle;
import com.flag.robot_dispatch.service.GeoCodingService;
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

    @Autowired
    public VehicleFilterController(VehicleFilterService vehicleFilterService, GeoCodingService geoCodingService) {
        this.vehicleFilterService = vehicleFilterService;
        this.geoCodingService = geoCodingService;
    }

    @GetMapping("/availablevehicles")
    public List<Vehicle> getFilteredVehicles(
            @RequestParam(name = "pickup_address") String pickupAddress,
            @RequestParam(name = "delivery_address") String deliveryAddress,
            @RequestParam(name = "delivery_time") String deliveryTime,
            @RequestParam(name = "delivery_volume") int volumeRequirement,
            @RequestParam(name = "delivery_weight") int weightRequirement) {

        Location pickupLocation = geoCodingService.getLatLng(pickupAddress);
        Location deliveryLocation = geoCodingService.getLatLng(deliveryAddress);
        // TODO: TimeUtil.getTimeRequirement
        int timeRequirement = 0;

        List<Vehicle> filteredAvailableVehicles = vehicleFilterService.findAvailableVehicles();
        List<VehicleFilter> vehicleFilterList =
                vehicleFilterService.buildFilterList(
                        pickupLocation,
                        deliveryLocation,
                        timeRequirement,
                        volumeRequirement,
                        weightRequirement);

        vehicleFilterService.filterAvailableVehicles(vehicleFilterList);
        return filteredAvailableVehicles;
    }

}
