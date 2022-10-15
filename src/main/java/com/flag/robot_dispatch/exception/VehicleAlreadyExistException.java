package com.flag.robot_dispatch.exception;

public class VehicleAlreadyExistException extends RuntimeException{
    public VehicleAlreadyExistException(String message){
        super(message);
    }
}
