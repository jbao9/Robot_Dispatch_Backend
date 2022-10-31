package com.flag.robot_dispatch.exception;

public class VehicleNotAvailableException extends RuntimeException {
    public VehicleNotAvailableException(String message){
        super(message);
    }
}
