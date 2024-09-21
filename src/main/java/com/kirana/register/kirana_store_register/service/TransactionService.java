package com.kirana.register.kirana_store_register.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kirana.register.kirana_store_register.model.Transaction;
import com.kirana.register.kirana_store_register.repository.TransactionRepository;

@Service
public class TransactionService {
  @Autowired
  private TransactionRepository transactionRepository;

  @Autowired
  private CurrencyConversionService currencyConversionService;

  public Transaction recordTransactionInDB(Transaction dto) throws Exception {
    double convertedAmount = currencyConversionService.convertCurrency(dto.getAmount(),
        dto.getOriginalCurrency(), dto.getTargetCurrency());
    Transaction transaction = new Transaction();
    transaction.setAmount(dto.getAmount());
    transaction.setOriginalCurrency(dto.getOriginalCurrency());
    transaction.setTargetCurrency(dto.getTargetCurrency());
    transaction.setConvertedAmount(convertedAmount);
    transaction.setTimeStamp(System.currentTimeMillis());
    System.out.println("Saving transaction: " + transaction);
    return transactionRepository.save(transaction);
  }
}
