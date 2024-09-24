package com.kirana.register.kirana_store_register.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.kirana.register.kirana_store_register.exceptions.TransactionExceptions;
import com.kirana.register.kirana_store_register.model.Transaction;
import com.kirana.register.kirana_store_register.repository.TransactionRepository;

public class TransactionTest {
  @Mock
  private TransactionRepository transactionRepository;

  @Mock
  private CurrencyConversionService currencyConversionService;

  @InjectMocks
  private TransactionService transactionService;

  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testRecordTransactionWithValidData() throws Exception {
    Transaction transaction = new Transaction();

    transaction.setId("123");
    transaction.setAmount(100.0);
    transaction.setOriginalCurrency("USD");
    transaction.setTargetCurrency("INR");
    transaction.setTimeStamp(System.currentTimeMillis());
    transaction.setConvertedAmount(8363.67849069);
    transaction.setTransactionType("credit");
    when(currencyConversionService.convertCurrency(100, "USD",
        "INR")).thenReturn(8363.67849069);
    when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

    Transaction result = transactionService.recordTransactionInDB(transaction);

    assertNotNull(result);
    assertEquals("123", result.getId());
    assertEquals(100, result.getAmount());
    assertEquals("USD", result.getOriginalCurrency());
    assertEquals("INR", result.getTargetCurrency());
    assertEquals(8363.67849069, result.getConvertedAmount());
  }

  @Test
  void testRecordTransactionWithNegativeAmount() {
    Transaction dto = new Transaction();
    dto.setAmount(-100);
    dto.setOriginalCurrency("USD");
    dto.setTargetCurrency("INR");
    dto.setTransactionType("credit");

    TransactionExceptions exception = assertThrows(TransactionExceptions.class,
        () -> {
          transactionService.recordTransactionInDB(dto);
        });

    assertEquals("Failed to record transaction: Invalid transaction amount", exception.getMessage());
  }

  @Test
  void testRecordTransactionInvalidCurrencyConversion() throws Exception {
    Transaction dto = new Transaction();
    dto.setAmount(100);
    dto.setOriginalCurrency("USD");
    dto.setTargetCurrency("INR");
    dto.setTransactionType("credit");

    when(currencyConversionService.convertCurrency(100, "USD", "INR"))
        .thenThrow(new RuntimeException("Conversion error"));

    TransactionExceptions exception = assertThrows(TransactionExceptions.class,
        () -> {
          transactionService.recordTransactionInDB(dto);
        });

    assertEquals("Failed to record transaction: Conversion error",
        exception.getMessage());
  }

}
