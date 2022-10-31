package com.flag.robot_dispatch.controller;

import com.flag.robot_dispatch.model.Vehicle;
import com.flag.robot_dispatch.service.DistanceService;
import com.flag.robot_dispatch.service.GeoCodingService;
import com.flag.robot_dispatch.service.TimeEstimateService;
import com.flag.robot_dispatch.service.VehicleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Time;
import java.util.List;

@RestController
public class TimeEstimateController {
    private DistanceService distanceService;
    private GeoCodingService geoCodingService;
    private TimeEstimateService timeEstimateService;

    private VehicleService vehicleService;

    public TimeEstimateController(DistanceService distanceService,
                                  GeoCodingService geoCodingService,
                                  TimeEstimateService timeEstimateService,
                                  VehicleService vehicleService) {
        this.distanceService = distanceService;
        this.geoCodingService = geoCodingService;
        this.timeEstimateService = timeEstimateService;
        this.vehicleService = vehicleService;
    }

    @GetMapping("/estimate_time")
    public String estimateDeliveryTime(
            @RequestParam("expect_pickup_time") String expectPickupTime,
            @RequestParam("pickup_address") String pickupAddress,
            @RequestParam("deliver_address") String deliverAddress,
            @RequestParam("vehicle_id") Long vehicleId
    ) {

        Double deliveryDistance = distanceService.getDistance(
                geoCodingService.getLatLng(pickupAddress),
                geoCodingService.getLatLng(deliverAddress));

        List<Vehicle> deliveryVehicles = vehicleService.findById(vehicleId);

        return timeEstimateService.getTimeEstimate(
                deliveryDistance,
                expectPickupTime,
                deliveryVehicles.get(0)
        );
    }

}
