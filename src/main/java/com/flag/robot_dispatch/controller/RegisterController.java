package com.flag.robot_dispatch.controller;

import com.flag.robot_dispatch.service.RegisterService;
import com.flag.robot_dispatch.model.User;
import com.flag.robot_dispatch.model.UserRole;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController {
   private RegisterService registerService;

   @Autowired
   public RegisterController(RegisterService registerService) {
      this.registerService = registerService;
   }

   @PostMapping("/register/guest")
   public void addGuest(@RequestBody User user) {
      registerService.add(user, UserRole.ROLE_GUEST);
   }

   @PostMapping("/register/admin")
   public void addAdmin(@RequestBody User user) {
      registerService.add(user, UserRole.ROLE_ADMIN);
   }
}
