package com.flag.robot_dispatch.exception;

public class OrderAlreadyExistException extends RuntimeException{
    public OrderAlreadyExistException(String message) {
        super(message);
    }
}

