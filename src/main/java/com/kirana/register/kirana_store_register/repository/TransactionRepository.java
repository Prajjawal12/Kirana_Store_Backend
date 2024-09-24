package com.kirana.register.kirana_store_register.repository;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.kirana.register.kirana_store_register.model.Transaction;

/**
 * Repository interface for managing Transaction entities.
 */
public interface TransactionRepository extends MongoRepository<Transaction, String> {

  /**
   * Finds transactions by original and target currency.
   *
   * @param originalCurrency the original currency
   * @param targetCurrency   the target currency
   * @return a list of transactions matching the criteria
   */
  List<Transaction> findByOriginalCurrencyAndTargetCurrency(String originalCurrency, String targetCurrency);

  /**
   * Finds transactions that occurred between two timestamps.
   *
   * @param startTime   the start timestamp
   * @param currentTime the end timestamp
   * @return a list of transactions within the given time range
   */
  List<Transaction> findByTimeStampBetween(long startTime, long currentTime);
}
