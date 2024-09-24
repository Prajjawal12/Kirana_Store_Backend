package com.kirana.register.kirana_store_register.exceptions;

/**
 * Custom exception class for report generation errors.
 */
public class ReportExceptions extends RuntimeException {
  /**
   * Constructs a new ReportExceptions with the specified detail message.
   *
   * @param message the detail message
   */
  public ReportExceptions(String message) {
    super(message);
  }
}