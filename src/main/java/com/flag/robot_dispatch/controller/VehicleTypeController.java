package com.flag.robot_dispatch.controller;

import com.flag.robot_dispatch.model.MachineType;
import com.flag.robot_dispatch.model.VehicleType;
import com.flag.robot_dispatch.service.VehicleTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

// TODO: FIX exception MachineType
@RestController
public class VehicleTypeController {
    private VehicleTypeService vehicleTypeService;

    @Autowired
    public VehicleTypeController(VehicleTypeService vehicleTypeService) {
        this.vehicleTypeService = vehicleTypeService;
    }

    @PostMapping("/vehicletype")
    public void addType(
            @RequestParam("type") String type,
            @RequestParam("speed") int speed,
            @RequestParam("height_capacity") int height,
            @RequestParam("length_capacity") int length,
            @RequestParam("width_capacity") int width,
            @RequestParam("weight_capacity") int weight,
            @RequestParam("delivery_range") int range
    ) {
        MachineType machineType = MachineType.valueOf(type);
        VehicleType vehicleType = new VehicleType.Builder()
                .setType(machineType)
                .setSpeed(speed)
                .setHeight_capacity(height)
                .setLength_capacity(length)
                .setWidth_capacity(width)
                .setWeight_capacity(weight)
                .setRange(range)
                .build();
        vehicleTypeService.add(vehicleType);
    }
}


//        MachineType machineType = MachineType.Drone_Heavy;
//        System.out.print(machineType);