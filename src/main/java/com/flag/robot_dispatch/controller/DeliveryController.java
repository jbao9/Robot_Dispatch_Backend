package com.flag.robot_dispatch.controller;

import com.flag.robot_dispatch.exception.InvalidOrderDateException;
import com.flag.robot_dispatch.model.DispatchCenter;
import com.flag.robot_dispatch.model.Order;
import com.flag.robot_dispatch.model.User;
import com.flag.robot_dispatch.model.Vehicle;
import com.flag.robot_dispatch.service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
public class DeliveryController {
    private DeliveryService deliveryService;

    @Autowired
    public DeliveryController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @GetMapping(value = "/deliveries")
    public List<Order> listDeliveryOrders(Principal principal) {
        return deliveryService.listByGuest(principal.getName());
    }

    @GetMapping(value = "/deliveries/tn/{trackingNo}")
    public Order getDeliveryOrderByTrackingNo(@PathVariable String trackingNo, Principal principal) {
        String orderIdStr = trackingNo.substring(6);
        Long orderId = Long.parseLong(orderIdStr);
        return deliveryService.findByIdAndGuest(orderId, principal.getName());
    }

    //for guest use
    @GetMapping(value = "/deliveries/{orderId}")
    public Order getDeliveryOrder(@PathVariable Long orderId, Principal principal) {
        return deliveryService.findByIdAndGuest(orderId, principal.getName());
    }

    //for admin use
    @GetMapping(value = "/deliveries_admin/{orderId}")
    public List<Order> getDeliveryOrder(@PathVariable Long orderId) {
        return deliveryService.listByOrderId(orderId);
    }

    // get delivery orders by order date range
    @GetMapping(value = "/deliveries_admin")
    public List<Order> listOrdersBetweenOrderDates(
            @RequestParam("order_date_start") String startDate,
            @RequestParam("order_date_end") String endDate) {
        if (LocalDate.parse(endDate).isBefore(LocalDate.parse(startDate)) || LocalDate.parse(endDate).isAfter(LocalDate.now())
        || LocalDate.parse(startDate).isAfter(LocalDate.now())) {
            throw new InvalidOrderDateException("Invalid order date");
        }  else {
            return deliveryService.listByOrderDate(LocalDate.parse(startDate), LocalDate.parse(endDate));
        }
    }

    @PostMapping("/deliveries")
    public String addDeliveryOrder(
            @RequestParam("pickup_address") String pickupAddress,
            @RequestParam("pickup_zipcode") int pickupZipcode,
            @RequestParam("deliver_address") String deliverAddress,
            @RequestParam("deliver_zipcode") int deliverZipcode,
            @RequestParam("weight") float weight,
            @RequestParam("length") float length,
            @RequestParam("width") float width,
            @RequestParam("height") float height,
            @RequestParam("description") String description,
            @RequestParam("expect_pickup_time") String expectPickupTime,
            @RequestParam("expect_delivery_date") String expectDeliveryDate,
            @RequestParam("vehicle_id") Long vehicleId,
            @RequestParam("center_id") Long centerId,
            Principal principal) {
        Order order = new Order.Builder()
                .setPickupAddress(pickupAddress)
                .setPickupZipcode(pickupZipcode)
                .setDeliverAddress(deliverAddress)
                .setDeliverZipcode(deliverZipcode)
                .setWeight(weight)
                .setLength(length)
                .setWidth(width)
                .setHeight(height)
                .setDescription(description)
                .setExpectPickupTime(LocalTime.parse(expectPickupTime))
                .setExpectDeliveryDate(LocalDate.parse(expectDeliveryDate))
                .setOrderDate(LocalDate.now())
                .setGuest(new User.Builder().setUsername(principal.getName()).build())
                .build();
        DispatchCenter dispatchCenter = new DispatchCenter();
        Vehicle vehicle = new Vehicle();
        order.setVehicle(vehicle.setId(vehicleId)).setCenter(dispatchCenter.setId(centerId));
        deliveryService.addDelivery(order, vehicleId);
        Long orderId = order.getOrderId();
        String trackingNo = "DD" + "0000" + orderId;
        return trackingNo;
    }

    @DeleteMapping("/deliveries/{orderId}")
    public void deleteDeliveryOrder(@PathVariable Long orderId, Principal principal) {
//    public void deleteDeliveryOrder(@PathVariable Long orderId) {
//        deliveryService.deleteDelivery(orderId);
        deliveryService.deleteDelivery(orderId, principal.getName());
    }

    @PostMapping("/deliveries/{deliveryOrderId}")
    public void updateDeliveryOrder(@PathVariable Order order, Principal principal) {
        deliveryService.updateDelivery(order);
    }
}
