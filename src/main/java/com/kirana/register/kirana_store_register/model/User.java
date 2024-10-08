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
  private String id; 

  private String username; 

  private String password; 

  private String role; 
}
