package com.flag.robot_dispatch.exception;

public class UserAlreadyExistException extends RuntimeException {
   public UserAlreadyExistException(String message) {
      super(message);
   }
}
