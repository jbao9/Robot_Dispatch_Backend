package com.flag.robot_dispatch.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "vehicle_type")
@JsonDeserialize(builder = VehicleType.Builder.class)
public class VehicleType {

//    , cascade = CascadeType.ALL
    @OneToMany(mappedBy = "type", fetch = FetchType.EAGER)
    private List<Vehicle> vehicle;

    @Id
    @Enumerated(value = EnumType.STRING)
    @JsonProperty("machine_type")
    private MachineType type;

    private int weight_capacity;

    private int length_capacity;

    private int width_capacity;

    private int height_capacity;

    private int speed;

//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "image_url")
//    private VehicleImage image;

    // TODO: Upgrade features
    //    private int unit_price;

    private int delivery_range;


    public VehicleType() {
    }

    public VehicleType(Builder builder) {
//        this.image = builder.image;
        this.type = builder.type;
        this.speed = builder.speed;
        this.width_capacity = builder.width_capacity;
        this.height_capacity = builder.height_capacity;
        this.length_capacity = builder.length_capacity;
        this.weight_capacity = builder.weight_capacity;
        this.delivery_range = builder.delivery_range;
    }


    public MachineType getType() {
        return type;
    }

    public int getWeight_capacity() {
        return weight_capacity;
    }

    public int getLength_capacity() { return length_capacity;}

    public int getWidth_capacity() { return width_capacity;}

    public int getHeight_capacity() {
        return height_capacity;
    }

    public int getSpeed() {
        return speed;
    }

    public int getRange() { return delivery_range; }

//    public VehicleImage getImage() {
//        return image;
//    }
//
//    public VehicleType setImages(VehicleImage image) {
//        this.image = image;
//        return this;
//    }

    public static class Builder {
        @JsonProperty("type")
        private MachineType type;
        @JsonProperty("weight_capacity")
        private int weight_capacity;
        @JsonProperty("length_capacity")
        private int length_capacity;
        @JsonProperty("speed")
        private int speed;
        @JsonProperty("width_capacity")
        private int width_capacity;
        @JsonProperty("height_capacity")
        private int height_capacity;
//
//        @JsonProperty("image")
//        private VehicleImage image;
        @JsonProperty("delivery_range")
        private int delivery_range;


        public Builder setType(MachineType type) {
            this.type = type;
            return this;
        }

        public Builder setWeight_capacity(int weight_capacity) {
            this.weight_capacity = weight_capacity;
            return this;
        }

        public Builder setHeight_capacity(int height_capacity) {
            this.height_capacity = height_capacity;
            return this;
        }

        public Builder setWidth_capacity(int width_capacity) {
            this.width_capacity = width_capacity;
            return this;
        }

        public Builder setLength_capacity(int length_capacity) {
            this.length_capacity = length_capacity;
            return this;
        }

        public Builder setSpeed(int speed) {
            this.speed = speed;
            return this;
        }

//        public Builder setImage(VehicleImage image) {
//            this.image = image;
//            return this;
//        }

        public Builder setRange(int delivery_range) {
            this.delivery_range = delivery_range;
            return this;
        }

        public VehicleType build() {
            return new VehicleType(this);
        }

    }

}
