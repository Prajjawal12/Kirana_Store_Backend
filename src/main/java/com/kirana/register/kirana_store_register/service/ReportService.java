package com.kirana.register.kirana_store_register.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.kirana.register.kirana_store_register.model.Report;
import com.kirana.register.kirana_store_register.model.Transaction;
import com.kirana.register.kirana_store_register.repository.ReportRepository;
import com.kirana.register.kirana_store_register.repository.TransactionRepository;

@Service
public class ReportService {

  @Autowired
  private ReportRepository reportRepository;

  @Autowired
  private TransactionRepository transactionRepository;

  @Autowired
  private CurrencyConversionService currencyConversionService;

  public Report generateReport(String reportType, String reportCurrency) throws Exception {
    long currentTime = System.currentTimeMillis();
    long startTime = 0;

    switch (reportType.toLowerCase()) {
      case "weekly":
        startTime = currentTime - (7 * 24 * 60 * 60 * 1000);
        break;
      case "monthly":
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, -1);
        startTime = calendar.getTimeInMillis();
        break;
      case "yearly":
        calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.YEAR, -1);
        startTime = calendar.getTimeInMillis();
        break;
      default:
        throw new IllegalArgumentException("Invalid Report Type");
    }

    List<Transaction> transactions = transactionRepository.findByTimeStampBetween(startTime, currentTime);
    double totalCredits = 0;
    double totalDebits = 0;

    for (Transaction transaction : transactions) {
      if ("credit".equalsIgnoreCase(transaction.getTransactionType())) {
        totalCredits += transaction.getConvertedAmount();
      } else if ("debit".equalsIgnoreCase(transaction.getTransactionType())) {
        totalDebits += transaction.getConvertedAmount();
      }
    }

    double netFlow = totalCredits - totalDebits;

    if (reportCurrency != null && !transactions.isEmpty()) {
      totalCredits = currencyConversionService.convertCurrency(totalCredits, transactions.get(0).getTargetCurrency(),
          reportCurrency);
      totalDebits = currencyConversionService.convertCurrency(totalDebits, transactions.get(0).getTargetCurrency(),
          reportCurrency);
      netFlow = totalCredits - totalDebits;
    }

    Report report = new Report();
    report.setReportType(reportType);
    report.setTotalCredits(totalCredits);
    report.setTotalDebits(totalDebits);
    report.setNetFlow(netFlow);
    report.setCurrency(reportCurrency != null ? reportCurrency : transactions.get(0).getTargetCurrency());
    report.setTimeStamp(System.currentTimeMillis());

    return reportRepository.save(report);
  }
}
