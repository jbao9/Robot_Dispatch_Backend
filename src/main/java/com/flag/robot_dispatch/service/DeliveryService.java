package com.flag.robot_dispatch.service;

import com.flag.robot_dispatch.exception.OrderAlreadyExistException;
import com.flag.robot_dispatch.exception.OrderNotExistException;
import com.flag.robot_dispatch.exception.VehicleNotExistException;
import com.flag.robot_dispatch.model.Order;
import com.flag.robot_dispatch.model.User;
import com.flag.robot_dispatch.model.Vehicle;
import com.flag.robot_dispatch.repository.OrderRepository;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class DeliveryService {
    private OrderRepository orderRepository;

    @Autowired
    public DeliveryService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
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


    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void addDelivery(Order order) {
        orderRepository.save(order);
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
