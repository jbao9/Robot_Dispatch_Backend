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

    private String location;

    @ManyToOne
    @JoinColumn(name = "machine_type")
    private VehicleType type;


    public Vehicle() {
    }

    private Vehicle(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.status = builder.status;
        this.location = builder.location;
        this.type = builder.type;


    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Status getstatus() {
        return status;
    }

    public String getlocation() {
        return location;
    }

    public VehicleType getType() {
        return type;
    }

    public static class Builder {

        @JsonProperty("id")
        private Long id;

        @JsonProperty("name")
        private String name;

        @JsonProperty("status")
        private Status status;

        @JsonProperty("location")
        private String location;

        @JsonProperty("type")
        private VehicleType type;


        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setstatus(Status status) {
            this.status = status;
            return this;
        }

        public Builder setlocation(String location) {
            this.location = location;
            return this;
        }

        public Builder setType(VehicleType type) {
            this.type = type;
            return this;
        }

        public Vehicle build() {
            return new Vehicle(this);
        }
    }


}



