package com.kirana.register.kirana_store_register.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kirana.register.kirana_store_register.model.Transaction;
import com.kirana.register.kirana_store_register.service.TransactionService;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
  @Autowired
  private TransactionService transactionService;

  @PostMapping
  public ResponseEntity<Transaction> recordTransaction(@RequestBody Transaction dto) throws Exception {
    if (!"credit".equalsIgnoreCase(dto.getTransactionType()) && !"debit".equalsIgnoreCase(dto.getTransactionType())) {
      return ResponseEntity.badRequest().build();
    }
    Transaction transaction = transactionService.recordTransactionInDB(dto);
    return ResponseEntity.status(HttpStatus.CREATED).body(transaction);
  }
}
