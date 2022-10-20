package com.flag.robot_dispatch.service;

import com.flag.robot_dispatch.exception.VehicleAlreadyExistException;
import com.flag.robot_dispatch.exception.VehicleNotExistException;
import com.flag.robot_dispatch.model.Vehicle;
import com.flag.robot_dispatch.repository.VehicleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class VehicleService {

    private VehicleRepository vehicleRepository;

    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public Vehicle addVehicle (Vehicle vehicle) {
        if(canAddVehicle(vehicle)){
            return vehicleRepository.save(vehicle);
        } else {
            throw new VehicleAlreadyExistException("Vehicle Already Exist");
        }
    }

    public void deleteVehicle(Long id) {
        try {
            vehicleRepository.deleteById(id);
        } catch (Exception e) {
            throw new VehicleNotExistException("Vehicle Does Not Exist");
        }
    }

    public Vehicle updateVehicle(Vehicle vehicle){
        Vehicle prevVehicle = vehicleRepository.getReferenceById(vehicle.getId());
        if(prevVehicle != null){
            return vehicleRepository.save(vehicle);
        } else {
            throw new VehicleNotExistException("No Vehicle Found for Update");
        }
    }

    private boolean canAddVehicle(Vehicle vehicle){
        return vehicleRepository.getReferenceById(vehicle.getId()) != null;
    }
}