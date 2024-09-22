package com.kirana.register.kirana_store_register.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.kirana.register.kirana_store_register.dto.CurrencyResponseDTO;

@Service
public class CurrencyConversionService {
  private static final String API_URL = "https://api.fxratesapi.com/latest";

  public double convertCurrency(double amount, String initialCurrency, String latterCurrency) throws Exception {
    RestTemplate restTemplate = new RestTemplate();
    CurrencyResponseDTO currencyResponse = restTemplate.getForObject(API_URL, CurrencyResponseDTO.class);
    if (currencyResponse != null && currencyResponse.isSuccess()) {
      double rate = currencyResponse.getRates().get(latterCurrency) / currencyResponse.getRates().get(initialCurrency);
      return amount * rate;
    }
    throw new Exception("Currency Conversion Failed!");
  }
}
