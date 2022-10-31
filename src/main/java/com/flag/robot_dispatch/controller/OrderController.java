package com.flag.robot_dispatch.controller;

import com.flag.robot_dispatch.model.Order;
import com.flag.robot_dispatch.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
public class OrderController {
    private OrderService orderService;
    @Autowired
    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }

    /**
     * List orders
     * */
    @GetMapping(value = "/orders")
    public List<Order> listOrders(Principal principal){
        return orderService.listByGuest(principal.getName());
    }

    /**
     * I'm not sure about this url -> need discuss
     * */
    @GetMapping(value = "/orders/{$orderId}")
    public Order getOrder(@PathVariable Long orderId){
        return orderService.findByOrderId(orderId);
    }

    @PostMapping(value = "/orders")
    public void addOrders(@RequestBody Order order){
        orderService.add(order);
    }

        Order order = new Order();
    @DeleteMapping(value = "/orders/{$orderId}")
    public void deleteOrders(@PathVariable Long orderId, Principal principal){
        orderService.delete(orderId,principal.getName());
    }


}
