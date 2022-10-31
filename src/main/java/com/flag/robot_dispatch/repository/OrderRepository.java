package com.flag.robot_dispatch.repository;

import com.flag.robot_dispatch.model.Order;
import com.flag.robot_dispatch.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByGuest(User guest);

    Order findByOrderIdAndGuest(Long orderId, User guest);

    Order findByOrderId(Long orderId);
}
