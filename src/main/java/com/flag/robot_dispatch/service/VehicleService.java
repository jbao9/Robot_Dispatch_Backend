package com.flag.robot_dispatch.service;

import com.flag.robot_dispatch.exception.VehicleAlreadyExistException;
import com.flag.robot_dispatch.exception.VehicleNotExistException;
import com.flag.robot_dispatch.exception.VehicleNotFoundException;
import com.flag.robot_dispatch.model.DispatchCenter;
import com.flag.robot_dispatch.model.Vehicle;
import com.flag.robot_dispatch.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;


import java.util.ArrayList;
import java.util.List;

@Service
public class VehicleService {

    private VehicleRepository vehicleRepository;

    @Autowired
    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public void addVehicle (Vehicle vehicle) {
        if  (canAddVehicle(vehicle)) {
            vehicleRepository.save(vehicle);
        } else {
            throw new VehicleAlreadyExistException("Vehicle already exists");
        }
    }

    public List<Vehicle> listByCenter(Long center_id) {
        List<Vehicle> list = vehicleRepository.findById(new DispatchCenter.Builder().setId(center_id).build());
        if (list.isEmpty()) {
            throw new VehicleNotFoundException("Vehicle not found");
        } else {
            return list;
        }
    }

    public List<Vehicle> findById(Long id) {
        List<Vehicle> value = new ArrayList<>();
        Vehicle vehicle = vehicleRepository.findByIdIs(id);

        if (vehicle == null) {
            throw new VehicleNotFoundException("Vehicle not found");
        } else {
            value.add(vehicle);
            return value;
        }
    }


    public void deleteVehicle(Long id) {
        try {
            vehicleRepository.deleteById(id);
        } catch (Exception e) {
            throw new VehicleNotExistException("Vehicle Does Not Exist");
        }
    }

//    update vehicle status available -> unavailable
//    public void assignVehicle(Long id) {
//        try {
//            vehicleRepository.
//        }
//    }
//
//    public Vehicle updateVehicle(Vehicle vehicle){
//        Vehicle prevVehicle = vehicleRepository.getReferenceById(vehicle.getId());
//        if(prevVehicle != null){
//            return vehicleRepository.save(vehicle);
//        } else {
//            throw new VehicleNotExistException("No Vehicle Found for Update");
//        }
//    }
//
    private boolean canAddVehicle(Vehicle vehicle){
        return !vehicleRepository.existsByName(vehicle.getName());
    }


}