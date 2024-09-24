package com.kirana.register.kirana_store_register.dto;

import lombok.Data;
import java.util.Map;

/**
 * DTO for the response from the currency conversion API.
 */
@Data
public class CurrencyResponseDTO {
  private boolean success; // Indicates if the API call was successful
  private Map<String, Double> rates; // A map of currency rates
}
