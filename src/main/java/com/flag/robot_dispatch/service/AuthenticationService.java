package com.flag.robot_dispatch.service;

import com.flag.robot_dispatch.exception.UserNotExistException;
import com.flag.robot_dispatch.util.JwtUtil;
import com.flag.robot_dispatch.model.Token;
import com.flag.robot_dispatch.model.User;
import com.flag.robot_dispatch.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

   private AuthenticationManager authenticationManager;
   private JwtUtil jwtUtil;

   @Autowired
   public AuthenticationService(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
      this.authenticationManager = authenticationManager;
      this.jwtUtil = jwtUtil;
   }

   public Token authenticate(User user, UserRole role) {
      Authentication auth = null;

      try {
         auth = authenticationManager.authenticate(
                 new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
         );
      } catch (AuthenticationException ex) {
         throw new UserNotExistException("Invalid user or password.");
      }

      if (auth == null || !auth.isAuthenticated() || !auth.getAuthorities().contains(new SimpleGrantedAuthority(role.name()))) {
         throw new UserNotExistException("Invalid user or password.");
      }

      return new Token(jwtUtil.generateToken(user.getUsername()));
   }
}
