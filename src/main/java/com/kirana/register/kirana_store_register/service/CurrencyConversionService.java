package com.kirana.register.kirana_store_register.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.kirana.register.kirana_store_register.model.CurrencyResponse;

@Service
public class CurrencyConversionService {
  private static final String CURRENCY_API_URL = "https://api.fxratesapi.com/latest";

  public double convertCurrency(double amount, String initialCurrency, String latterCurrency) throws Exception {
    RestTemplate restTemplate = new RestTemplate();

    CurrencyResponse currencyResponse = restTemplate.getForObject(CURRENCY_API_URL, CurrencyResponse.class);
    if (currencyResponse != null && currencyResponse.isSuccess()) {
      double rate = currencyResponse.getRates().get(latterCurrency) / currencyResponse.getRates().get(initialCurrency);
      return amount * rate;
    }
    throw new Exception("Currency Conversion Failed!");
  }
}
