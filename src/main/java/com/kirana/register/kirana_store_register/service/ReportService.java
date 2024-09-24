package com.kirana.register.kirana_store_register.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.kirana.register.kirana_store_register.exceptions.CustomExceptions;
import com.kirana.register.kirana_store_register.exceptions.ReportExceptions;
import com.kirana.register.kirana_store_register.model.Report;
import com.kirana.register.kirana_store_register.model.Transaction;
import com.kirana.register.kirana_store_register.repository.ReportRepository;
import com.kirana.register.kirana_store_register.repository.TransactionRepository;

/**
 * Service for generating financial reports based on transactions.
 */
@Service
public class ReportService {
  private static final Logger logger = LoggerFactory.getLogger(ReportService.class);

  @Autowired
  private ReportRepository reportRepository;

  @Autowired
  private TransactionRepository transactionRepository;

  @Autowired
  private CurrencyConversionService currencyConversionService;

  /**
   * Generates a financial report based on the specified report type and currency.
   *
   * @param reportType     The type of report to generate (e.g., weekly, monthly,
   *                       yearly).
   * @param reportCurrency The currency for the report.
   * @return The generated report.
   * @throws Exception If an error occurs while generating the report.
   */
  public Report generateReport(String reportType, String reportCurrency) throws Exception {
    try {
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
          throw new ReportExceptions("Invalid Report Type: " + reportType);
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

      Report newReport = reportRepository.save(report);
      logger.info("Generated report: {}", newReport);

      return newReport;
    } catch (Exception e) {
      throw new CustomExceptions("Error generating report: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
