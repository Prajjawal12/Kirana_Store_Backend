package com.kirana.register.kirana_store_register.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.kirana.register.kirana_store_register.exceptions.UserAuthException;
import com.kirana.register.kirana_store_register.model.User;
import com.kirana.register.kirana_store_register.service.UserService;

import java.util.Map;

/**
 * Controller for user authentication, including registration and login.
 */
@RestController
@RequestMapping("/api/auth")
public class UserController {
  private static final Logger logger = LoggerFactory.getLogger(UserController.class);

  @Autowired
  private UserService userService;

  /**
   * Registers a new user in the system.
   *
   * @param user the user details for registration
   * @return a ResponseEntity containing the registered user or an error message
   */
  @PostMapping("/register")
  public ResponseEntity<?> registerUser(@RequestBody User user) {
    logger.info("Attempting to register user: {}", user.getUsername());

    // Check if the username is already taken
    if (userService.findByUsername(user.getUsername()) != null) {
      logger.warn("Username already taken: {}", user.getUsername());
      return ResponseEntity.badRequest().body("Username is already taken");
    }

    // Register the user
    User registeredUser = userService.registerUser(user);
    return ResponseEntity.ok(registeredUser);
  }

  /**
   * Logs in a user and returns an authentication token.
   *
   * @param loginData a map containing the username and password
   * @return a ResponseEntity containing the authentication token or an error
   *         message
   */
  @PostMapping("/login")
  public ResponseEntity<?> loginUser(@RequestBody Map<String, String> loginData) {
    String username = loginData.get("username");
    String password = loginData.get("password");

    try {
      // Authenticate the user
      String token = userService.authenticateUser(username, password);
      logger.info("User {} logged in successfully.", username);
      return ResponseEntity.ok(Map.of("token", token));
    } catch (UserAuthException e) {
      logger.error("Authentication error: {}", e.getMessage());
      return ResponseEntity.status(e.getStatus()).body(e.getMessage());
    } catch (Exception e) {
      logger.error("Unexpected error during login: {}", e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
    }
  }
}
