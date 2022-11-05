package com.flag.robot_dispatch.controller;

import com.flag.robot_dispatch.exception.InvalidInputException;
import com.flag.robot_dispatch.exception.InvalidOrderDateException;
import com.flag.robot_dispatch.model.*;
import com.flag.robot_dispatch.service.DeliveryService;
import com.flag.robot_dispatch.service.DispatchCenterService;
import com.flag.robot_dispatch.service.GeoCodingService;
import com.flag.robot_dispatch.service.VehicleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.*;

@RestController
public class DeliveryController {
    private DeliveryService deliveryService;
    private GeoCodingService geoCodingService;
    private DispatchCenterService dispatchCenterService;

    @Autowired
    public DeliveryController(DeliveryService deliveryService, GeoCodingService geoCodingService, DispatchCenterService dispatchCenterService) {
        this.dispatchCenterService = dispatchCenterService;
        this.deliveryService = deliveryService;
        this.geoCodingService = geoCodingService;
    }

    //guest list all orders of his/her own
    @GetMapping(value = "/deliveries")
    public List<Order> listDeliveryOrders(Principal principal) {
        return deliveryService.listByGuest(principal.getName());
    }
    
    //guest get order by orderId
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

    //admin get order by orderId
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

    // guest create order
    @PostMapping("/deliveries")
    public HashMap<String, Object> addDeliveryOrder(
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
        DispatchCenter center = dispatchCenterService.getCenterById(centerId);
        Double centerLat = center.getLat();
        Double centerLon = center.getLon();

        Location pickupLocation = geoCodingService.getLatLng(pickupAddress);

        HashMap<String, Object> deliveryMap = new HashMap<>();
        deliveryMap.put("OriginLat", centerLat);
        deliveryMap.put("OriginLon", centerLon);
        deliveryMap.put("DesLoc", pickupLocation);
        deliveryMap.put("TrackNo", trackingNo);
        return deliveryMap;
    }

    // guest delete order
    @DeleteMapping("/deliveries/{orderId}")
    public void deleteDeliveryOrder(@PathVariable Long orderId, Principal principal) {
//    public void deleteDeliveryOrder(@PathVariable Long orderId) {
//        deliveryService.deleteDelivery(orderId);
        deliveryService.deleteDelivery(orderId, principal.getName());
    }

    // guest update order
    @PutMapping("/deliveries/{orderId}")
    public void updateDeliveryOrder(@PathVariable Long orderId,
                                    @RequestParam(value = "weight",required = false) Float weight,
                                    @RequestParam(value = "length", required = false) Float length,
                                    @RequestParam(value = "width", required = false) Float width,
                                    @RequestParam(value = "height", required= false) Float height,
                                    @RequestParam(value ="pickup_address",required = false) String pickupAddress,
                                    @RequestParam(value = "pickup_zipcode",required = false) Integer pickupZipcode,
                                    @RequestParam(value ="deliver_address",required = false) String deliverAddress,
                                    @RequestParam(value = "deliver_zipcode",required = false) Integer deliverZipcode,
                                    @RequestParam(value ="description",required = false) String description,
                                    @RequestParam(value ="expect_pickup_time",required = false) String expectPickupTime,
                                    @RequestParam(value ="expect_delivery_date",required = false) String expectDeliveryDate,
                                    Principal principal) {
        Order existing = deliveryService.findByIdAndGuest(orderId, principal.getName());

        Order toUpdate = new Order();

        if (weight != null && weight != 0){
            toUpdate.setWeight(weight.floatValue());
        }
        if (length != null && length != 0){
            toUpdate.setLength(length.floatValue());
        }
        if (width != null && width != 0){
            toUpdate.setWidth(width.floatValue());
        }
        if (height != null && height != 0){
            toUpdate.setHeight(height);
        }
        if (pickupAddress != "") {
            toUpdate.setPickupAddress(pickupAddress);
        }
        if (pickupZipcode != null && pickupZipcode != 0){
            toUpdate.setPickupZipcode(pickupZipcode);
        }
        if (deliverAddress != "") {
            toUpdate.setDeliverAddress(deliverAddress);
        }
        if (deliverZipcode != null && deliverZipcode != 0) {
            toUpdate.setDeliverZipcode(deliverZipcode);
        }
        if (description != ""){
            toUpdate.setDescription(description);
        }
        if (expectPickupTime != null && expectPickupTime != "") {
            try {
                toUpdate.setExpectPickupTime(LocalTime.parse(expectPickupTime));
            } catch (java.time.format.DateTimeParseException e) {
                throw new InvalidInputException("Invalid pickup time input");
            }
        }
        if (expectDeliveryDate != null && expectDeliveryDate != "") {
            try {
                toUpdate.setExpectDeliveryDate(LocalDate.parse(expectDeliveryDate));
            } catch(java.time.format.DateTimeParseException e) {
                throw new InvalidInputException("Invalid pickup delivery date input");
            }
        }

        copyNonNullProperties(toUpdate, existing);

        deliveryService.updateDelivery(existing);
    }

    // admin update order
    @PutMapping("/deliveries_admin/{orderId}")
    public void adminUpdateDeliveryOrder(@PathVariable Long orderId,
                                    @RequestParam(value = "weight",required = false) Float weight,
                                    @RequestParam(value = "length", required = false) Float length,
                                    @RequestParam(value = "width", required = false) Float width,
                                    @RequestParam(value = "height", required= false) Float height,
                                    @RequestParam(value ="pickup_address",required = false) String pickupAddress,
                                    @RequestParam(value = "pickup_zipcode",required = false) Integer pickupZipcode,
                                    @RequestParam(value ="deliver_address",required = false) String deliverAddress,
                                    @RequestParam(value = "deliver_zipcode",required = false) Integer deliverZipcode,
                                    @RequestParam(value ="description",required = false) String description,
                                    @RequestParam(value ="expect_pickup_time",required = false) String expectPickupTime,
                                    @RequestParam(value ="expect_delivery_date",required = false) String expectDeliveryDate,
                                    @RequestParam(value = "delivered_date", required = false) String deliveredDate,
                                    @RequestParam(value = "vehicle_id", required = false) Long vehicleId,
                                    @RequestParam(value = "center_id", required = false) Long centerId) {
        Order existing = deliveryService.findByOrderId(orderId);

        Order toUpdate = new Order();

        if (weight != null && weight != 0){
            toUpdate.setWeight(weight);
        }
        if (length != null && length != 0){
            toUpdate.setLength(length);
        }
        if (width != null && width != 0){
            toUpdate.setWidth(width);
        }
        if (height != null && height != 0){
            toUpdate.setHeight(height);
        }
        if (pickupAddress != "") {
            toUpdate.setPickupAddress(pickupAddress);
        }
        if (pickupZipcode != null && pickupZipcode != 0){
            toUpdate.setPickupZipcode(pickupZipcode);
        }
        if (deliverAddress != "") {
            toUpdate.setDeliverAddress(deliverAddress);
        }
        if (deliverZipcode != null && deliverZipcode != 0) {
            toUpdate.setDeliverZipcode(deliverZipcode);
        }
        if (description != ""){
            toUpdate.setDescription(description);
        }
        if (expectPickupTime != null && expectPickupTime != "") {
            try {
                toUpdate.setExpectPickupTime(LocalTime.parse(expectPickupTime));
            } catch (java.time.format.DateTimeParseException e) {
                throw new InvalidInputException("Invalid pickup time input");
            }
        }
        if (expectDeliveryDate != null && expectDeliveryDate != "") {
            try {
                toUpdate.setExpectDeliveryDate(LocalDate.parse(expectDeliveryDate));
            } catch(java.time.format.DateTimeParseException e) {
                throw new InvalidInputException("Invalid pickup delivery date input");
            }
        }
        if (deliveredDate != null && deliveredDate != "") {
            try {
                toUpdate.setDeliveredDate(LocalDate.parse(deliveredDate));
            } catch (java.time.format.DateTimeParseException e) {
                throw new InvalidInputException("Invalid delivered date input");
            }
        }
        if (vehicleId != null && vehicleId != 0) {
            Vehicle vehicle = new Vehicle();
            toUpdate.setVehicle(vehicle.setId(vehicleId));
        }
        if (centerId != null && centerId != 0) {
            DispatchCenter dispatchCenter = new DispatchCenter();
            toUpdate.setCenter(dispatchCenter.setId(centerId));
        }

        copyNonNullProperties(toUpdate, existing);

        deliveryService.updateDelivery(existing);
    }

                                            //toUpdate      //existing
    public static void copyNonNullProperties(Order src, Order target) {
         BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
//      System.out.println("--------------> "+ Arrays.toString(getNullPropertyNames(src)));
//       System.out.println("-----> Src Description " + src.getDescription());
//       System.out.println("-----> Target Description " + target.getDescription());
    }

    // return a list of fields which contains the value of null or 0
    public static String[] getNullPropertyNames (Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for(java.beans.PropertyDescriptor pd : pds) {

            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());

            if (pd.getPropertyType() == Float.TYPE){
               if ( ((Float)srcValue) == 0 ) {
                   emptyNames.add(pd.getName());
               }
            }

            if (pd.getPropertyType() == Integer.TYPE) {
                if (((Integer)srcValue) == 0) {
                    emptyNames.add(pd.getName());
                }
            }

        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

}
