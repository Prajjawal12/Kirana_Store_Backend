package com.kirana.register.kirana_store_register.dto;

import lombok.Data;

/**
 * DTO for user registration and authentication requests.
 */
@Data
public class UserRequestDTO {
  private String username;
  private String password; 
}
