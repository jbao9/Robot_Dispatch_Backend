package com.flag.robot_dispatch.repository;

import com.flag.robot_dispatch.model.DispatchCenter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DispatchCenterRepository extends JpaRepository<DispatchCenter, Long> {
}
