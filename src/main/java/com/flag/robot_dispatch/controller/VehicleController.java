package com.flag.robot_dispatch.controller;

import com.flag.robot_dispatch.model.*;
import com.flag.robot_dispatch.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

// TODO: Not Done Yet.

@RestController
public class VehicleController {
    private VehicleService vehicleService;


    @Autowired
    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }


    @PostMapping(value = "/vehicles")
    public void addVehicle(
            @RequestParam("name") String name,
            @RequestParam("status") Status status,
            @RequestParam("center_id") DispatchCenter center_id,
            @RequestParam("machine_type") VehicleType machine_type
    ) {
        Vehicle vehicle = new Vehicle.Builder()
                .setName(name).setStatus(status).setType(machine_type).setLocation(center_id).build();
        vehicleService.addVehicle(vehicle);
    }


// TODO: add get and delete and update methods
//    ! This is P0 features.
}
