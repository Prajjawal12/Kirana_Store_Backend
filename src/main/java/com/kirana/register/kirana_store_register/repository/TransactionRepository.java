package com.kirana.register.kirana_store_register.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.kirana.register.kirana_store_register.model.Transaction;

public interface TransactionRepository extends MongoRepository<Transaction, String> {
  List<Transaction> findByTimeStampBetween(long startTime, long currentTime);

}
