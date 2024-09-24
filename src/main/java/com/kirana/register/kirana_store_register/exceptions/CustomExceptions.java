package com.kirana.register.kirana_store_register.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Custom exception class that includes HTTP status codes.
 */
public class CustomExceptions extends RuntimeException {
  private HttpStatus status;

  /**
   * Constructs a new CustomExceptions with the specified detail message and HTTP
   * status.
   *
   * @param message the detail message
   * @param status  the HTTP status associated with this exception
   */
  public CustomExceptions(String message, HttpStatus status) {
    super(message);
    this.status = status;
  }

  /**
   * Returns the associated HTTP status.
   *
   * @return the HTTP status
   */
  public HttpStatus getStatus() {
    return status; // Returns the associated HTTP status
  }
}
