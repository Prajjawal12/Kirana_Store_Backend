package com.kirana.register.kirana_store_register.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import com.kirana.register.kirana_store_register.model.User;
import com.kirana.register.kirana_store_register.service.UserService;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class UserController {
  @Autowired
  private UserService userService;

  @PostMapping("/register")
  public ResponseEntity<?> registerUser(@RequestBody User user) {
    if (userService.findByUsername(user.getUsername()) != null) {
      return ResponseEntity.badRequest().body("Username is already taken");
    }
    User registeredUser = userService.registerUser(user);
    return ResponseEntity.ok(registeredUser);
  }

  @PostMapping("/login")
  public ResponseEntity<?> loginUser(@RequestBody Map<String, String> loginData) {
    String username = loginData.get("username");
    String password = loginData.get("password");

    try {
      String token = userService.authenticateUser(username, password);
      return ResponseEntity.ok(Map.of("token", token));
    } catch (UsernameNotFoundException e) {
      return ResponseEntity.status(404).body("User not found");
    } catch (Exception e) {
      return ResponseEntity.status(401).body("Invalid credentials");
    }
  }
}