package com.kirana.register.kirana_store_register.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;

@Document(collection = "currency_cache")
@Data
public class Cache {
  @Id
  private String id;

  private String originalCurrency;
  private String targetCurrency;
  private double rate;
  private long timestamp;
}
