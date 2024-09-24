package com.kirana.register.kirana_store_register.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kirana.register.kirana_store_register.exceptions.TransactionExceptions;
import com.kirana.register.kirana_store_register.model.Transaction;
import com.kirana.register.kirana_store_register.repository.TransactionRepository;

/**
 * Service class for handling transaction operations.
 */
@Service
public class TransactionService {
  /**
   * Records a transaction in the database.
   *
   * @param dto the transaction data to be saved
   * @return the saved transaction
   * @throws TransactionExceptions if the transaction amount is invalid or saving
   *                               fails
   */
  private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);

  @Autowired
  private TransactionRepository transactionRepository;

  @Autowired
  private CurrencyConversionService currencyConversionService;

  public Transaction recordTransactionInDB(Transaction dto) throws TransactionExceptions {
    try {
      if (dto.getAmount() <= 0) {
        throw new TransactionExceptions("Invalid transaction amount");
      }
      double convertedAmount = currencyConversionService.convertCurrency(dto.getAmount(),
          dto.getOriginalCurrency(), dto.getTargetCurrency());
      System.out.println("Converted amount is coming as" + convertedAmount);
      Transaction transaction = new Transaction();
      transaction.setAmount(dto.getAmount());
      transaction.setOriginalCurrency(dto.getOriginalCurrency());
      transaction.setTargetCurrency(dto.getTargetCurrency());
      transaction.setConvertedAmount(convertedAmount);
      transaction.setTimeStamp(System.currentTimeMillis());
      transaction.setTransactionType(dto.getTransactionType());

      Transaction savedTransaction = transactionRepository.save(transaction);
      logger.info("Transaction recorded with ID: {}", savedTransaction);
      return savedTransaction;

    } catch (Exception e) {
      logger.error("Error recording transaction: {}", e.getMessage());
      throw new TransactionExceptions("Failed to record transaction: " + e.getMessage());
    }
  }
}
