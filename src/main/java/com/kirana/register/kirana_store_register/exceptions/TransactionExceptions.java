package com.kirana.register.kirana_store_register.exceptions;

/**
 * Custom exception class for transaction-related errors.
 */
public class TransactionExceptions extends RuntimeException {
  /**
   * Constructs a new TransactionExceptions with the specified detail message.
   *
   * @param message the detail message
   */
  public TransactionExceptions(String message) {
    super(message);
  }
}