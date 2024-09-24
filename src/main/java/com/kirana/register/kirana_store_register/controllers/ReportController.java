package com.kirana.register.kirana_store_register.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kirana.register.kirana_store_register.exceptions.ReportExceptions;
import com.kirana.register.kirana_store_register.model.Report;
import com.kirana.register.kirana_store_register.service.ReportService;

/**
 * Controller for generating reports.
 */
@RestController
@RequestMapping("/api/reports")
public class ReportController {
  private static final Logger logger = LoggerFactory.getLogger(ReportController.class);

  @Autowired
  private ReportService reportService;

  /**
   * Generates a weekly report for the specified currency.
   *
   * @param currency the currency for which the report is generated
   * @return ResponseEntity containing the generated report
   * @throws Exception if an error occurs during report generation
   */
  @GetMapping("/weekly")
  public ResponseEntity<Report> generateWeeklyReport(@RequestParam(required = false) String currency) throws Exception {
    logger.info("Received request for weekly report with currency: {}", currency);
    try {
      Report report = reportService.generateReport("weekly", currency);
      return ResponseEntity.ok(report);
    } catch (ReportExceptions e) {
      logger.error("Report generation error: {}", e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
  }

  /**
   * Generates a monthly report for the specified currency.
   *
   * @param currency the currency for which the report is generated
   * @return ResponseEntity containing the generated report
   * @throws Exception if an error occurs during report generation
   */
  @GetMapping("/monthly")
  public ResponseEntity<Report> generateMonthlyReport(@RequestParam(required = false) String currency)
      throws Exception {
    logger.info("Received request for monthly report with currency: {}", currency);
    try {
      Report report = reportService.generateReport("monthly", currency);
      return ResponseEntity.ok(report);
    } catch (ReportExceptions e) {
      logger.error("Report generation error: {}", e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
  }

  /**
   * Generates a yearly report for the specified currency.
   *
   * @param currency the currency for which the report is generated
   * @return ResponseEntity containing the generated report
   * @throws Exception if an error occurs during report generation
   */
  @GetMapping("/yearly")
  public ResponseEntity<Report> generateYearlyReport(@RequestParam(required = false) String currency) throws Exception {
    logger.info("Received request for yearly report with currency: {}", currency);
    try {
      Report report = reportService.generateReport("yearly", currency);
      return ResponseEntity.ok(report);
    } catch (ReportExceptions e) {
      logger.error("Report generation error: {}", e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
  }
}
