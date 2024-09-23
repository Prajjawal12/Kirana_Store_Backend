package com.kirana.register.kirana_store_register.service;

import com.kirana.register.kirana_store_register.dto.CurrencyResponseDTO;
import com.kirana.register.kirana_store_register.model.Cache;
import com.kirana.register.kirana_store_register.repository.CacheRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class CurrencyConversionService {

  private static final String API_URL = "https://api.fxratesapi.com/latest";
  private static final long CACHE_EXPIRY = 3600000;

  @Autowired
  private CacheRepository cacheRepository;

  public double convertCurrency(double amount, String originalCurrency, String targetCurrency) throws Exception {

    Optional<Cache> cachedRateOpt = cacheRepository.findByOriginalCurrencyAndTargetCurrency(originalCurrency,
        targetCurrency);
    if (cachedRateOpt.isPresent()) {
      Cache cachedRate = cachedRateOpt.get();
      if (System.currentTimeMillis() - cachedRate.getTimestamp() < CACHE_EXPIRY) {
        return amount * cachedRate.getRate();
      }
    }

    RestTemplate restTemplate = new RestTemplate();
    CurrencyResponseDTO currencyResponse = restTemplate.getForObject(API_URL, CurrencyResponseDTO.class);

    if (currencyResponse != null && currencyResponse.isSuccess()) {
      double rate = currencyResponse.getRates().get(targetCurrency) / currencyResponse.getRates().get(originalCurrency);

      Cache newCache = new Cache();
      newCache.setOriginalCurrency(originalCurrency);
      newCache.setTargetCurrency(targetCurrency);
      newCache.setRate(rate);
      newCache.setTimestamp(System.currentTimeMillis());
      cacheRepository.save(newCache);

      return amount * rate;
    }
    throw new Exception("Currency Conversion Failed!");
  }
}
