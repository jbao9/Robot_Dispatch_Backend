package com.flag.robot_dispatch.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "delivery_order")
@JsonDeserialize(builder = User.Builder.class)
public class Order implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty("order_id")
    private Long orderId;

    @JsonProperty("order_date")
    private LocalDate orderDate;

    private float weight;

    private float length;

    private float width;

    private float height;

    @JsonProperty("pickup_address")
    private String pickupAddress;

    @JsonProperty("pickup_zipcode")
    private int pickupZipcode;

    @JsonProperty("deliver_address")
    private String deliverAddress;

    @JsonProperty("deliver_zipcode")
    private int deliverZipcode;

    private String description;

    @JsonProperty("expect_pickup_time")
    private LocalTime expectPickupTime;

    @JsonProperty("expect_delivery_date")
    private LocalDate expectDeliveryDate;

    @JsonProperty("delivered_date")
    private LocalDate deliveredDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User guest;

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    @ManyToOne
    @JoinColumn(name = "center_id")
    private DispatchCenter center;

    public Order() {}

    public Order(Builder builder) {
        this.orderId = builder.orderId;
        this.orderDate = builder.orderDate;
        this.weight = builder.weight;
        this.length = builder.length;
        this.width = builder.width;
        this.height = builder.height;
        this.pickupAddress = builder.pickupAddress;
        this.pickupZipcode = builder.pickupZipcode;
        this.deliverAddress = builder.deliverAddress;
        this.deliverZipcode = builder.deliverZipcode;
        this.description = builder.description;
        this.expectPickupTime = builder.expectPickupTime;
        this.expectDeliveryDate = builder.expectDeliveryDate;
        this.deliveredDate = builder.deliveredDate;
        this.guest = builder.guest;
        this.vehicle = builder.vehicleId;
        this.center = builder.centerId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public float getWeight() {
        return weight;
    }

    public float getLength() {
        return length;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public String getPickupAddress() {
        return pickupAddress;
    }

    public int getPickupZipcode() {
        return pickupZipcode;
    }

    public String getDeliverAddress() {
        return deliverAddress;
    }

    public int getDeliverZipcode() {
        return deliverZipcode;
    }

    public String getDescription() {
        return description;
    }

    public LocalTime getExpectPickupTime() {
        return expectPickupTime;
    }

    public LocalDate getExpectDeliveryDate() {
        return expectDeliveryDate;
    }

    public LocalDate getDeliveredDate() {
        return deliveredDate;
    }

    public User getGuest() {
        return guest;
    }

    public Order setGuest(User guest) {
        this.guest = guest;
        return this;
    }

    public Vehicle getVehicleId() {
        return vehicle;
    }

    public DispatchCenter getCenterId() {
        return center;
    }

    public static class Builder {
        @JsonProperty("order_id")
        private Long orderId;

        @JsonProperty("order_date")
        private LocalDate orderDate;

        @JsonProperty("weight")
        private float weight;

        @JsonProperty("length")
        private float length;

        @JsonProperty("width")
        private float width;

        @JsonProperty("height")
        private float height;

        @JsonProperty("pickup_address")
        private String pickupAddress;

        @JsonProperty("pickup_zipcode")
        private int pickupZipcode;

        @JsonProperty("deliver_address")
        private String deliverAddress;

        @JsonProperty("deliver_zipcode")
        private int deliverZipcode;

        @JsonProperty("description")
        private String description;

        @JsonProperty("expect_pickup_time")
        private LocalTime expectPickupTime;

        @JsonProperty("expect_delivery_date")
        private LocalDate expectDeliveryDate;

        @JsonProperty("delivered_date")
        private LocalDate deliveredDate;

        @JsonProperty("guest")
        private User guest;

        @JsonProperty("vehicle_id")
        private Vehicle vehicleId;

        @JsonProperty("center_id")
        private DispatchCenter centerId;

        public Builder setId(Long orderId) {
            this.orderId = orderId;
            return this;
        }

        public Builder setOrderDate(LocalDate orderDate) {
            this.orderDate = orderDate;
            return this;
        }

        public Builder setWeight(float weight) {
            this.weight = weight;
            return this;
        }

        public Builder setLength(float length) {
            this.length = length;
            return this;
        }

        public Builder setWidth(float width) {
            this.width = width;
            return this;
        }

        public Builder setHeight(float height) {
            this.height = height;
            return this;
        }

        public Builder setPickupAddress(String pickupAddress) {
            this.pickupAddress = pickupAddress;
            return this;
        }

        public Builder setPickupZipcode(int pickupZipcode) {
            this.pickupZipcode = pickupZipcode;
            return this;
        }

        public Builder setDeliverAddress(String deliverAddress) {
            this.deliverAddress = deliverAddress;
            return this;
        }

        public Builder setDeliverZipcode(int deliverZipcode) {
            this.deliverZipcode = deliverZipcode;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setExpectPickupTime(LocalTime expectPickupTime) {
            this.expectPickupTime = expectPickupTime;
            return this;
        }

        public Builder setExpectDeliveryDate(LocalDate expectDeliveryDate) {
            this.expectDeliveryDate = expectDeliveryDate;
            return this;
        }

        public Builder setDeliveredDate(LocalDate deliveredDate) {
            this.deliveredDate = deliveredDate;
            return this;
        }

        public Builder setGuest(User guest) {
            this.guest = guest;
            return this;
        }

        public Builder setVehicleId(Vehicle vehicleId) {
            this.vehicleId = vehicleId;
            return this;
        }

        public Builder setCenterId(DispatchCenter centerId) {
            this.centerId = centerId;
            return this;
        }

        public Order build() {
            return new Order(this);
        }

    }

}
