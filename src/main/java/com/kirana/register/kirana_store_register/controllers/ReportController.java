package com.kirana.register.kirana_store_register.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kirana.register.kirana_store_register.model.Report;
import com.kirana.register.kirana_store_register.service.ReportService;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

  @Autowired
  private ReportService reportService;

  @PostMapping("/weekly")
  public Report generateWeeklyReport(@RequestParam(required = false) String currency) throws Exception {
    return reportService.generateReport("weekly", currency);
  }

  @PostMapping("/monthly")
  public Report generateMonthlyReport(@RequestParam(required = false) String currency) throws Exception {
    return reportService.generateReport("monthly", currency);
  }

  @PostMapping("/yearly")
  public Report generateYearlyReport(@RequestParam(required = false) String currency) throws Exception {
    return reportService.generateReport("yearly", currency);
  }
}
