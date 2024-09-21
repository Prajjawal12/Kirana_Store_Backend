package com.kirana.register.kirana_store_register.model;

import lombok.Data;
import java.util.Map;

@Data
public class CurrencyResponse {
  private boolean success;
  private Map<String, Double> rates;
}
