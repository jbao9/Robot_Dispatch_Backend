package com.flag.robot_dispatch.controller;

import com.flag.robot_dispatch.model.User;
import com.flag.robot_dispatch.model.UserRole;
import com.flag.robot_dispatch.model.Token;
import com.flag.robot_dispatch.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {
   private AuthenticationService authenticationService;

   @Autowired
   public AuthenticationController(AuthenticationService authenticationService) {
      this.authenticationService = authenticationService;
   }

   @PostMapping("/authenticate/guest")
   public Token authenticateGuest(@RequestBody User user) {
      return authenticationService.authenticate(user, UserRole.ROLE_GUEST);
   }

   @PostMapping("/authenticate/admin")
   public Token authenticateHost(@RequestBody User user) {
      return authenticationService.authenticate(user, UserRole.ROLE_ADMIN);
   }
}
