package com.kirana.register.kirana_store_register.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;

/**
 * Represents a user in the system.
 */
@Data
@Document(collection = "users")
public class User {
  @Id
  private String id; // Unique identifier for the user

  private String username; // Username of the user

  private String password; // Password of the user

  private String role; // Role of the user (e.g., admin, user)
}
