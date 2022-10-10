package com.flag.robot_dispatch.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "vehicle_type")
@JsonDeserialize(builder = VehicleType.Builder.class)
public class VehicleType {
    @Id
//    @OneToMany(mappedBy = "vehicle_type", fetch = FetchType.EAGER)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    @JsonProperty("machine_type")
    private MachineType type;

    private int weight_capacity;

    private int volume_capacity;

    private int speed;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "image_url", referencedColumnName = "id")
    private VehicleImage image;

    // Upgrade features
//    private int range;
//    private int unit_price;

    public VehicleType() {
    }

    private VehicleType(Builder builder) {

    }

    public static class Builder {
        @JsonProperty("id")
        private Long id;
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

        public Builder setId(Long id) {
            this.id = id;
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

        public VehicleType build() {return new VehicleType(this);}

    }

}
