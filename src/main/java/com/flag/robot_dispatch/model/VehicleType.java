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
    @Enumerated(value = EnumType.STRING)
    @JsonProperty("machine_type")
    private MachineType type;

    private int weight_capacity;

    private int volume_capacity;

    private int speed;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "image_url")
    private VehicleImage image;

    // TODO: Upgrade features
//    private int range;
//    private int unit_price;

    public VehicleType() {
    }

    private VehicleType(Builder builder) {
        this.vehicle = builder.vehicle;
        this.image = builder.image;
        this.type = builder.type;
        this.speed = builder.speed;
        this.volume_capacity = builder.volume_capacity;
        this.weight_capacity = builder.weight_capacity;
    }

    public List<Vehicle> getvehicle() {
        return vehicle;
    }

    public MachineType getType() {
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

    public VehicleImage getImage() {
        return image;
    }

    public static class Builder {
        @JsonProperty("vehicle")
        private List<Vehicle> vehicle;
        @JsonProperty("type")
        private MachineType type;
        @JsonProperty("weight_capacity")
        private int weight_capacity;
        @JsonProperty("volume_capacity")
        private int volume_capacity;
        @JsonProperty("speed")
        private int speed;

        @JsonProperty("image")
        private VehicleImage image;

        public Builder setId(List<Vehicle> vehicle) {
            this.vehicle = vehicle;
            return this;
        }

        public Builder setType(MachineType type) {
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

        public Builder setImage(VehicleImage image) {
            this.image = image;
            return this;
        }

        public VehicleType build() {
            return new VehicleType(this);
        }

    }

}
