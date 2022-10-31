package com.flag.robot_dispatch.controller;

import com.flag.robot_dispatch.model.*;
import com.flag.robot_dispatch.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.lang.invoke.VarHandle;
import java.util.List;

import static com.flag.robot_dispatch.model.MachineType.*;

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
            @RequestParam("status") String status,
            @RequestParam("center_id") Long center_id,
            @RequestParam("vehicle_type") String type
    ) {


        Status status1 = Status.valueOf(status);
        MachineType machineType = MachineType.valueOf(type);

        DispatchCenter dispatchCenter = new DispatchCenter();

        Vehicle vehicle = new Vehicle.Builder()
                .setName(name).setStatus(status1).build();
        vehicle.setType(new VehicleType.Builder().setType(machineType).build());
        vehicle.setCenter(dispatchCenter.setId(center_id));
        vehicleService.addVehicle(vehicle);
    }


// TODO: add get and delete and update methods
//    This is P0 features.

    @GetMapping(value = "/vehicles/center/{center}")
    public List<Vehicle> listVehicleByCenter(@PathVariable Long center) {
        return vehicleService.listByCenter(center);
    }

    @GetMapping(value = "/vehicles/{id}")
    public List<Vehicle> getVehicleById(@PathVariable Long id) {
        return vehicleService.findById(id);
    }

    @DeleteMapping(value = "/vehicles/{id}")
    public void deleteVehicle(@PathVariable Long id) {
        vehicleService.deleteVehicle(id);
    }



}
