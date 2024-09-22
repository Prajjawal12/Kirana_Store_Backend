package com.kirana.register.kirana_store_register.dto;

import lombok.Data;
import java.util.Map;

@Data
public class CurrencyResponseDTO {
  private boolean success;
  private Map<String, Double> rates;
}
