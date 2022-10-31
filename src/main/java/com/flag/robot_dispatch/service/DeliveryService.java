package com.flag.robot_dispatch.service;

import com.flag.robot_dispatch.exception.*;
import com.flag.robot_dispatch.model.Order;
import com.flag.robot_dispatch.model.Status;
import com.flag.robot_dispatch.model.User;
import com.flag.robot_dispatch.model.Vehicle;
import com.flag.robot_dispatch.repository.OrderRepository;
import com.flag.robot_dispatch.repository.VehicleRepository;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class DeliveryService {
    private OrderRepository orderRepository;
    private VehicleRepository vehicleRepository;

    @Autowired
    public DeliveryService(OrderRepository orderRepository, VehicleRepository vehicleRepository) {
        this.orderRepository = orderRepository;
        this.vehicleRepository = vehicleRepository;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void deleteDelivery(Long orderId, String username) throws OrderNotExistException {
        Order order = orderRepository.findByOrderIdAndGuest(orderId, new User.Builder().setUsername(username).build());
        if (order == null) {
            throw new OrderNotExistException("Order doesn't exist");
        }
        if (order != null) {
            orderRepository.deleteById(orderId);
        }
    }

 // Check if the vehicle is available. if Yes, then assign the task to the vehicle.
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void addDelivery(Order order, Long vehicleId) {
        Status status = vehicleRepository.findStatusById(vehicleId);
        if (status == Status.available) {
            orderRepository.save(order);
            vehicleRepository.updateStatus(vehicleId, Status.unavailable);
        } else {
            throw new VehicleNotAvailableException("Vehicle is on duty, Please Select another vehicle");
        }

    }

    public Order updateDelivery(Order order){
        Order prevOrder = orderRepository.getReferenceById(order.getOrderId());

        if(prevOrder != null){
            return orderRepository.save(order);
        } else {
            throw new OrderNotExistException("No Order Found for Update");
        }
    }

    public List<Order> listByGuest(String username) {
        return orderRepository.findByGuest(new User.Builder().setUsername(username).build());
    }

    public Order findByIdAndGuest(Long orderId, String username) throws OrderAlreadyExistException {
        Order order= orderRepository.findByOrderIdAndGuest(orderId, new User.Builder().setUsername(username).build());
        if (order == null) {
            throw new OrderNotExistException("Order doesn't exist");
        }
        return order;
    }
}
