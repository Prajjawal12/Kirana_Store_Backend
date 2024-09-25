package com.kirana.register.kirana_store_register.dto;

import lombok.Data;

/**
 * DTO for user authentication responses.
 */
@Data
public class UserResponseDTO {
  private String token; 
  private String role;
}
