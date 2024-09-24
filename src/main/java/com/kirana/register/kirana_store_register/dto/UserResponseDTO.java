package com.kirana.register.kirana_store_register.dto;

import lombok.Data;

/**
 * DTO for user authentication responses.
 */
@Data
public class UserResponseDTO {
  private String token; // Authentication token for the user
  private String role; // Role of the user (e.g., admin, user)
}
