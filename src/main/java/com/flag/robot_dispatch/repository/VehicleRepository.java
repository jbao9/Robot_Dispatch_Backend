package com.flag.robot_dispatch.repository;

import com.flag.robot_dispatch.model.DispatchCenter;
import com.flag.robot_dispatch.model.Status;
import com.flag.robot_dispatch.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    Boolean existsByName(String name);

    // TODO: findByStatus
    List<Vehicle> findByStatus(Status status);

    @Query("SELECT v FROM Vehicle v WHERE v.location = :center")
    List<Vehicle> findById(DispatchCenter center);

    Vehicle findByIdIs(Long id);
}
