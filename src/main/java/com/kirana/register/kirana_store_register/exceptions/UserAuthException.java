package com.kirana.register.kirana_store_register.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Custom exception class for user authentication errors.
 */
public class UserAuthException extends RuntimeException {
  private final HttpStatus status;

  /**
   * Constructs a new UserAuthException with the specified detail message and HTTP
   * status.
   *
   * @param message the detail message
   * @param status  the HTTP status associated with this exception
   */
  public UserAuthException(String message, HttpStatus status) {
    super(message);
    this.status = status;
  }

  /**
   * Returns the associated HTTP status.
   *
   * @return the HTTP status
   */
  public HttpStatus getStatus() {
    return status;
  }
}