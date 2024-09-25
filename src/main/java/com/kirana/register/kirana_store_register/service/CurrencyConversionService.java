package com.kirana.register.kirana_store_register.service;

import com.kirana.register.kirana_store_register.dto.CurrencyResponseDTO;
import com.kirana.register.kirana_store_register.model.Cache;
import com.kirana.register.kirana_store_register.repository.CacheRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

/**
 * Service for handling currency conversion logic.
 */
@Service
public class CurrencyConversionService {

  private static final String API_URL = "https://api.fxratesapi.com/latest"; 
                                                                            
  private static final long CACHE_EXPIRY = 3600000; 
  private static final Logger logger = LoggerFactory.getLogger(CurrencyConversionService.class);

  @Autowired
  private CacheRepository cacheRepository; 

  /**
   * Converts an amount from one currency to another.
   *
   * @param amount           The amount to be converted.
   * @param originalCurrency The currency code of the original currency.
   * @param targetCurrency   The currency code of the target currency.
   * @return The converted amount.
   * @throws Exception If currency conversion fails.
   */
  public double convertCurrency(double amount, String originalCurrency, String targetCurrency) throws Exception {
    try {
      
      Optional<Cache> cachedRateOpt = cacheRepository.findLatestByOriginalCurrencyAndTargetCurrency(originalCurrency,
          targetCurrency);
      if (cachedRateOpt.isPresent()) {
        Cache cachedRate = cachedRateOpt.get();
        if (System.currentTimeMillis() - cachedRate.getTimestamp() < CACHE_EXPIRY) {
          logger.info("Cache hit for currency conversion: {} to {}", originalCurrency, targetCurrency);
          return amount * cachedRate.getRate();
        }
      }

      
      RestTemplate restTemplate = new RestTemplate();
      CurrencyResponseDTO currencyResponse = restTemplate.getForObject(API_URL, CurrencyResponseDTO.class);

   
      if (currencyResponse != null && currencyResponse.isSuccess()) {
        double rate = currencyResponse.getRates().get(targetCurrency)
            / currencyResponse.getRates().get(originalCurrency);

       
        try {
          Cache newCache = new Cache();
          newCache.setOriginalCurrency(originalCurrency);
          newCache.setTargetCurrency(targetCurrency);
          newCache.setRate(rate);
          newCache.setTimestamp(System.currentTimeMillis());
          cacheRepository.save(newCache);
          logger.info("Currency conversion rate cached: {} to {}", originalCurrency, targetCurrency);
        } catch (Exception e) {
          logger.warn("Failed to cache currency conversion rate: {} to {}", originalCurrency, targetCurrency);
        }

        return amount * rate; 
      } else {
        throw new Exception("Currency Conversion API Failed!");
      }

    } catch (Exception e) {
      logger.error("Currency conversion failed for {} to {}: {}", originalCurrency, targetCurrency, e.getMessage());
      throw new Exception("Currency Conversion Failed: " + e.getMessage());
    }
  }
}
