package com.flag.robot_dispatch.controller;

import com.flag.robot_dispatch.model.MachineType;
import com.flag.robot_dispatch.model.VehicleType;
import com.flag.robot_dispatch.service.VehicleTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

// TODO: FIX exception MachineType

@Controller
public class VehicleTypeController {
    private VehicleTypeService vehicleTypeService;


    @Autowired
    public VehicleTypeController(VehicleTypeService vehicleTypeService) {
        this.vehicleTypeService = vehicleTypeService;

    }

    @PostMapping("/vehicletype")
    public void addType(
//            @RequestBody VehicleType vehicleType
            @RequestParam("type") String type,
            @RequestParam("speed") int speed,
            @RequestParam("volume_capacity") int volume,
            @RequestParam("weight_capacity") int weight,
//            @RequestParam("image") MultipartFile[] images,
            @RequestParam("delivery_range") int range
    ) {
//        MachineType machineType = MachineType.valueOf(type);
//        MachineType machineType = MachineType.Drone_Heavy;
//        System.out.print(machineType);
        VehicleType vehicleType = new VehicleType.Builder()
                .setType(type)
                .setSpeed(speed)
                .setVolume_capacity(volume)
                .setWeight_capacity(weight)
                .setRange(range)
                .build();
        vehicleTypeService.add(vehicleType);

    }

}
