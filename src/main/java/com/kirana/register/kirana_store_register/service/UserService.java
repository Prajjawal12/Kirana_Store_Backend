package com.kirana.register.kirana_store_register.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.kirana.register.kirana_store_register.utils.JwtUtil;
import com.kirana.register.kirana_store_register.exceptions.UserAuthException;
import com.kirana.register.kirana_store_register.model.User;
import com.kirana.register.kirana_store_register.repository.UserRepository;

/**
 * Service class for handling transaction operations.
 */
@Service
public class UserService {
  private static final Logger logger = LoggerFactory.getLogger(UserService.class);
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private JwtUtil jwtUtil;

  /**
   * Registers a new user by saving their details in the repository.
   *
   * @param user The user object containing registration details.
   * @return The registered user.
   */
  public User registerUser(User user) {
    logger.info("Registering user: {}", user.getUsername());
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    return userRepository.save(user);
  }

  /**
   * Authenticates a user based on username and password.
   *
   * @param username The username of the user.
   * @param password The password of the user.
   * @return A JWT token if authentication is successful.
   * @throws UserAuthException if authentication fails.
   */
  public String authenticateUser(String username, String password) throws UserAuthException {
    logger.info("Authenticating user: {}", username);
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new UserAuthException("User Not Found", HttpStatus.NOT_FOUND));

    boolean isPasswordMatch = passwordEncoder.matches(password.trim(), user.getPassword());

    if (isPasswordMatch) {
      return jwtUtil.generateToken(user.getUsername(), user.getRole());
    } else {
      logger.warn("Invalid credentials for user: {}", username);
      throw new UserAuthException("Invalid username or password", HttpStatus.UNAUTHORIZED);
    }
  }

  /**
   * Finds a user by their username.
   *
   * @param username The username of the user to be found.
   * @return The user object if found, null otherwise.
   */
  public User findByUsername(String username) {
    return userRepository.findByUsername(username).orElse(null);
  }
}
