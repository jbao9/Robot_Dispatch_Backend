package com.flag.robot_dispatch.service;

import com.flag.robot_dispatch.exception.UserAlreadyExistException;
import com.flag.robot_dispatch.model.Authority;
import com.flag.robot_dispatch.model.User;
import com.flag.robot_dispatch.model.UserRole;
import com.flag.robot_dispatch.repository.AuthorityRepository;
import com.flag.robot_dispatch.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class RegisterService {
   private UserRepository userRepository;
   private AuthorityRepository authorityRepository;
   private PasswordEncoder passwordEncoder;

   @Autowired
   public RegisterService(UserRepository userRepository,
                          AuthorityRepository authorityRepository, PasswordEncoder passwordEncoder) {
      this.userRepository = userRepository;
      this.authorityRepository = authorityRepository;
      this.passwordEncoder = passwordEncoder;
   }

   @Transactional(isolation = Isolation.SERIALIZABLE)
   public void add(User user, UserRole role) throws UserAlreadyExistException {
      if (userRepository.existsById(user.getUsername())) {
         throw new UserAlreadyExistException("User already exists");
      }
      user.setPassword(passwordEncoder.encode(user.getPassword()));
      user.setEnabled(true);
      userRepository.save(user);
      authorityRepository.save(new Authority(user.getUsername(), role.name()));
   }
}
