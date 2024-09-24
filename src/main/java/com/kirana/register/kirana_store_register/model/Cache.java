package com.kirana.register.kirana_store_register.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;

/**
 * Represents a cached currency conversion rate.
 */
@Document(collection = "currency_cache")
@CompoundIndexes({
    @CompoundIndex(name = "currency_pair_idx", def = "{'originalCurrency': 1, 'targetCurrency': 1}", unique = true)
})
@Data
public class Cache {
  @Id
  private String id; // Unique identifier for the cache entry

  private String originalCurrency; // The original currency code

  private String targetCurrency; // The target currency code

  private double rate; // The conversion rate from original to target currency

  private long timestamp; // The timestamp when the cache entry was created
}
