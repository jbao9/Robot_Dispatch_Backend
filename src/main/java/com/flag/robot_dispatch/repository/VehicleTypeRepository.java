package com.flag.robot_dispatch.repository;

import com.flag.robot_dispatch.model.MachineType;
import com.flag.robot_dispatch.model.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleTypeRepository extends JpaRepository<VehicleType, String> {

    VehicleType findByTypeIs(MachineType type);
}
