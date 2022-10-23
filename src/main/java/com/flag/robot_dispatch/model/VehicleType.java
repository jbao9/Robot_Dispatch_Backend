package com.flag.robot_dispatch.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "vehicle_type")
@JsonDeserialize(builder = VehicleType.Builder.class)
public class VehicleType {

    @OneToMany(mappedBy = "type", fetch = FetchType.EAGER)
    private List<Vehicle> vehicle;

    @Id
//    @Enumerated(value = EnumType.STRING)
    @JsonProperty("machine_type")
    private String type;

    private int weight_capacity;

    private int volume_capacity;

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
        this.volume_capacity = builder.volume_capacity;
        this.weight_capacity = builder.weight_capacity;
        this.delivery_range = builder.delivery_range;
    }


    public String getType() {
        return type;
    }

    public int getWeight_capacity() {
        return weight_capacity;
    }

    public int getVolume_capacity() {
        return volume_capacity;
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
        private String type;
        @JsonProperty("weight_capacity")
        private int weight_capacity;
        @JsonProperty("volume_capacity")
        private int volume_capacity;
        @JsonProperty("speed")
        private int speed;
//
//        @JsonProperty("image")
//        private VehicleImage image;
        @JsonProperty("delivery_range")
        private int delivery_range;


        public Builder setType(String type) {
            this.type = type;
            return this;
        }

        public Builder setWeight_capacity(int weight_capacity) {
            this.weight_capacity = weight_capacity;
            return this;
        }

        public Builder setVolume_capacity(int volume_capacity) {
            this.volume_capacity = volume_capacity;
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
