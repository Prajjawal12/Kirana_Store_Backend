package com.kirana.register.kirana_store_register.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.kirana.register.kirana_store_register.utils.JwtUtil;
import com.kirana.register.kirana_store_register.model.User;
import com.kirana.register.kirana_store_register.repository.UserRepository;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private JwtUtil jwtUtil;

  public User registerUser(User user) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    return userRepository.save(user);
  }

  public String authenticateUser(String username, String password) throws Exception {
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));

    boolean isPasswordMatch = passwordEncoder.matches(password.trim(), user.getPassword());

    if (isPasswordMatch) {
      return jwtUtil.generateToken(user.getUsername(), user.getRole());
    } else {
      throw new Exception("Invalid username or password");
    }
  }

  public User findByUsername(String username) {
    return userRepository.findByUsername(username).orElse(null);
  }
}
