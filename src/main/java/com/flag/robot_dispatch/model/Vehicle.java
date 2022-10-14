package com.flag.robot_dispatch.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "vehicle")
@JsonDeserialize(builder = Vehicle.Builder.class)
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @Enumerated(value = EnumType.STRING)
    @JsonProperty("status")
    private Status status;

    @ManyToOne
    @JoinColumn(name = "center_id")
    private DispatchCenter location;

    @ManyToOne
    @JoinColumn(name = "machine_type")
    private VehicleType type;


//    private int range;


    public Vehicle() {
    }

    private Vehicle(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.status = builder.status;
        this.location = builder.location;
        this.type = builder.type;
//        this.range = builder.range;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Status getStatus() {
        return status;
    }

    public DispatchCenter getLocation() {
        return location;
    }

    public VehicleType getType() {
        return type;
    }

//    public int getRange() { return range; }

    public static class Builder {

        @JsonProperty("id")
        private Long id;

        @JsonProperty("name")
        private String name;

        @JsonProperty("status")
        private Status status;

        @JsonProperty("location")
        private DispatchCenter location;

        @JsonProperty("type")
        private VehicleType type;

//        @JsonProperty("range")
//        private int range;


        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setStatus(Status status) {
            this.status = status;
            return this;
        }

        public Builder setLocation(DispatchCenter location) {
            this.location = location;
            return this;
        }

        public Builder setType(VehicleType type) {
            this.type = type;
            return this;
        }

//        public Builder setRange(int range) {
//            this.range = range;
//            return this;
//        }

        public Vehicle build() {
            return new Vehicle(this);
        }
    }


}



