package com.flag.robot_dispatch.service;

import com.flag.robot_dispatch.exception.OrderAlreadyExistException;
import com.flag.robot_dispatch.exception.OrderNotExistException;
import com.flag.robot_dispatch.model.Order;
import com.flag.robot_dispatch.model.User;
import com.flag.robot_dispatch.repository.DispatchCenterRepository;
import com.flag.robot_dispatch.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderService {
    private OrderRepository orderRepository;
    private DispatchCenterRepository dispatchCenterRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, DispatchCenterRepository dispatchCenterRepository){
        this.orderRepository = orderRepository;
        this.dispatchCenterRepository = dispatchCenterRepository;
    }

    /**
     * orderRepository does not have findByOrderId
     * And I created it
     * */
    public Order findByOrderId(Long orderId){
        return orderRepository.findByOrderId(orderId);
    }

    public List<Order> listByGuest(String username){
        return orderRepository.findByGuest(new User.Builder().setUsername(username).build());
    }
    /**
     * Delivery order upload
     * */
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void add(Order order) throws OrderAlreadyExistException{
        if(orderRepository.existsById(order.getOrderId())){
            throw new OrderAlreadyExistException("Order Already Created");
        }
        orderRepository.save(order);
    }

    /*
     * Delivery order delete
     * */
    @Transactional(isolation =  Isolation.SERIALIZABLE)
    public void delete(Long orderId, String username){
        Order order = orderRepository.findByOrderIdAndGuest(orderId, new User.Builder().setUsername(username).build());
        if(order == null){
            throw new OrderNotExistException("No Such Order");
        }
        orderRepository.deleteById(order.getOrderId());
    }

}
