package com.kirana.register.kirana_store_register.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;

/**
 * Represents a transaction in the system.
 */
@Data
@Document(collection = "transactions")
public class Transaction {
  @Id
  private String id;

  private double amount; 

  private String originalCurrency; 

  private String targetCurrency; 

  private double convertedAmount; 

  private long timeStamp; 

  private String transactionType;
}
