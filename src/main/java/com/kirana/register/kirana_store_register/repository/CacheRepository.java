package com.kirana.register.kirana_store_register.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import java.util.Optional;
import com.kirana.register.kirana_store_register.model.Cache;

/**
 * Repository interface for accessing currency cache data.
 */
public interface CacheRepository extends MongoRepository<Cache, String> {

  /**
   * Finds the latest cache entry by original and target currency.
   *
   * @param originalCurrency The original currency code.
   * @param targetCurrency   The target currency code.
   * @return An Optional containing the latest cache entry, if found.
   */
  @Query(value = "{'originalCurrency': ?0, 'targetCurrency': ?1}", sort = "{ 'timestamp': -1 }")
  Optional<Cache> findLatestByOriginalCurrencyAndTargetCurrency(String originalCurrency, String targetCurrency);
}
