package com.kirana.register.kirana_store_register.dto;

import lombok.Data;
import java.util.Map;

/**
 * DTO for the response from the currency conversion API.
 */
@Data
public class CurrencyResponseDTO {
  private boolean success;
  private Map<String, Double> rates; 
}
