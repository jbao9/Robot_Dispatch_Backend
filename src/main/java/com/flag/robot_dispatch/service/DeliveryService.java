package com.flag.robot_dispatch.service;

import com.flag.robot_dispatch.exception.OrderAlreadyExistException;
import com.flag.robot_dispatch.exception.OrderNotExistException;
import com.flag.robot_dispatch.exception.VehicleNotExistException;
import com.flag.robot_dispatch.model.Order;
import com.flag.robot_dispatch.model.Vehicle;
import com.flag.robot_dispatch.repository.OrderRepository;
import org.aspectj.weaver.ast.Or;

public class DeliveryService {
    private OrderRepository orderRepository;
    public void deleteDelivery(Long id) {
        try {
            orderRepository.deleteById(id);
        } catch (Exception e) {
            throw new OrderNotExistException("Order Does Not Exist");
        }
    }
    public void addDelivery(Order order) {
        if (canAddDelivery(order)) {
            orderRepository.save(order);
        } else {
            throw new OrderAlreadyExistException("Order Already Exist");
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

    private boolean canAddDelivery(Order order) {
        return orderRepository.getReferenceById(order.getOrderId()) == null;
    }
}
