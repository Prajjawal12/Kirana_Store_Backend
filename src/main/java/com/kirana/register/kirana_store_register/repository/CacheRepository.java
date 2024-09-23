package com.kirana.register.kirana_store_register.repository;

import com.kirana.register.kirana_store_register.model.Cache;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface CacheRepository extends MongoRepository<Cache, String> {
  Optional<Cache> findByOriginalCurrencyAndTargetCurrency(String baseCurrency, String targetCurrency);
}
