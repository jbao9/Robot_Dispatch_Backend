package com.flag.robot_dispatch.exception;

public class UserNotExistException extends RuntimeException {
   public UserNotExistException(String message) {
      super(message);
   }
}
