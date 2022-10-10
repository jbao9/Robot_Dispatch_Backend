package com.flag.robot_dispatch.repository;

import com.flag.robot_dispatch.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    // TODO: findByStatus
}
