package com.flag.robot_dispatch.service;

import com.flag.robot_dispatch.exception.VehicleAlreadyExistException;
import com.flag.robot_dispatch.exception.VehicleNotExistException;
import com.flag.robot_dispatch.model.DispatchCenter;
import com.flag.robot_dispatch.model.Vehicle;
import com.flag.robot_dispatch.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;


import java.util.List;

@Service
public class VehicleService {

    private VehicleRepository vehicleRepository;

    @Autowired
    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public void addVehicle (Vehicle vehicle) {
            vehicleRepository.save(vehicle);
    }

    public List<Vehicle> listByCenter(Long center_id) {

        return vehicleRepository.findById(new DispatchCenter.Builder().setId(center_id).build());
    }

    public Vehicle listByName(String name) {
        return vehicleRepository.findByName(name);
    }


//    public void deleteVehicle(Long id) {
//        try {
//            vehicleRepository.deleteById(id);
//        } catch (Exception e) {
//            throw new VehicleNotExistException("Vehicle Does Not Exist");
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
//    private boolean canAddVehicle(Vehicle vehicle){
//        return vehicleRepository.getReferenceById(vehicle.getId()) != null;
//    }


}