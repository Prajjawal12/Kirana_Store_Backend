package com.kirana.register.kirana_store_register.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kirana.register.kirana_store_register.exceptions.TransactionExceptions;
import com.kirana.register.kirana_store_register.model.Transaction;
import com.kirana.register.kirana_store_register.service.TransactionService;

/**
 * Controller for managing transactions.
 */
@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
  private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);

  @Autowired
  private TransactionService transactionService;

  /**
   * Records a transaction in the system.
   *
   * @param dto   the transaction details
   * @param token the authorization token
   * @return a ResponseEntity containing the recorded transaction or an error
   *         message
   */
  @PostMapping
  public ResponseEntity<?> recordTransaction(@RequestBody Transaction dto,
      @RequestHeader("Authorization") String token) {
    logger.info("Received transaction request: {}", dto);

  
    if (!"credit".equalsIgnoreCase(dto.getTransactionType()) && !"debit".equalsIgnoreCase(dto.getTransactionType())) {
      logger.warn("Invalid transaction type: {}", dto.getTransactionType());
      return ResponseEntity.badRequest().build();
    }

    try {
    
      Transaction transaction = transactionService.recordTransactionInDB(dto);
      return ResponseEntity.status(HttpStatus.CREATED).body(transaction);
    } catch (TransactionExceptions e) {
      logger.error("Transaction error: {}", e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("Error recording transaction: " + e.getMessage());
    }
  }
}
