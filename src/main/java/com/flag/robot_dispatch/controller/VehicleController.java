package com.flag.robot_dispatch.controller;

import com.flag.robot_dispatch.model.*;
import com.flag.robot_dispatch.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.lang.invoke.VarHandle;

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
            @RequestParam("id") Long id,
            @RequestParam("name") String name,
            @RequestParam("status") String status,
            @RequestParam("center_id") Long center_id,
            @RequestParam("vehicle_type") String type
    ) {

    /**
        get objects from frontend could be problematic
        This can be a factory pattern or a switch statement to get object from backend
        MachineType machine_type = null;
        switch (type) {
            case "Robot_light":
                machine_type = Robot_light;
                break;
            case "Robot_Heavy":
                machine_type = Robot_Heavy;
                break;
            case "Drone_light":
                machine_type = Drone_light;
                break;
            case "Drone_Heavy":
                machine_type = Drone_Heavy;
                break;
        }

        if (machine_type == null) {
            throw new RuntimeException();
        }
*/
        Status status1 = Status.valueOf(status);
        MachineType machineType = MachineType.valueOf(type);

        DispatchCenter dispatchCenter = new DispatchCenter();

        Vehicle vehicle = new Vehicle.Builder()
                .setName(name).setStatus(status1).build();
//        .setType(machineType).setLocation(center_id)
        vehicle.setType(new VehicleType.Builder().setType(machineType).build());
        vehicle.setCenter(dispatchCenter.setId(center_id));
        vehicleService.addVehicle(vehicle);
    }


// TODO: add get and delete and update methods
//    ! This is P0 features.
}
