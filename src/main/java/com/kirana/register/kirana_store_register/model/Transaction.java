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
  private String id; // Unique identifier for the transaction

  private double amount; // Amount of the transaction

  private String originalCurrency; // Original currency of the transaction

  private String targetCurrency; // Target currency of the transaction

  private double convertedAmount; // Converted amount in target currency

  private long timeStamp; // Timestamp of the transaction

  private String transactionType; // Type of the transaction (credit/debit)
}
