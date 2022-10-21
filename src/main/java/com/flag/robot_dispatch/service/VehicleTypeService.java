package com.flag.robot_dispatch.service;

//import com.flag.robot_dispatch.model.VehicleImage;
import com.flag.robot_dispatch.model.VehicleType;
import com.flag.robot_dispatch.repository.VehicleTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VehicleTypeService {
    private VehicleTypeRepository vehicleTypeRepository;

    @Autowired
    public VehicleTypeService(VehicleTypeRepository vehicleTypeRepository) {
        this.vehicleTypeRepository = vehicleTypeRepository;
    }

    public void add(VehicleType type) {
//        type.setImages(stayImages);
        vehicleTypeRepository.save(type);
    }
}
