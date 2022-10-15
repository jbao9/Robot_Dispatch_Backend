package com.flag.robot_dispatch.exception;

public class VehicleNotExistException extends RuntimeException{
    public VehicleNotExistException(String message){
        super(message);
    }
}
