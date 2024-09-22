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
    User registeredUser = userRepository.save(user);

    String token = jwtUtil.generateToken(registeredUser.getUsername(), registeredUser.getRole());
    System.out.println("Token generated for user: " + registeredUser.getUsername() + " - " + token);

    return registeredUser;
  }

  public String authenticateUser(String username, String password) throws Exception {
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));

    System.out.println("Authenticating user: " + username);
    System.out.println("Input password: " + password);

    boolean isPasswordMatch = passwordEncoder.matches(password.trim(), user.getPassword());
    System.out.println("Stored hashed password: " + user.getPassword());
    System.out.println("Password match: " + isPasswordMatch);

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
