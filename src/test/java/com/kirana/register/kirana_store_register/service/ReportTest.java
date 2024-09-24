package com.kirana.register.kirana_store_register.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.kirana.register.kirana_store_register.controllers.ReportController;
import com.kirana.register.kirana_store_register.exceptions.ReportExceptions;
import com.kirana.register.kirana_store_register.model.Report;

public class ReportTest {
  @InjectMocks
  private ReportController reportController;

  @Mock
  private ReportService reportService;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void testGenerateWeeklyReport_Success() throws Exception {

    Report mockReport = new Report();
    mockReport.setReportType("weekly");
    when(reportService.generateReport("weekly", null)).thenReturn(mockReport);

    ResponseEntity<Report> response = reportController.generateWeeklyReport(null);

    assertEquals(200, response.getStatusCodeValue());
    assertEquals(mockReport, response.getBody());
    verify(reportService, times(1)).generateReport("weekly", null);
  }

  @Test
  public void testGenerateWeeklyReport_Failure() throws Exception {
    when(reportService.generateReport("weekly", null)).thenThrow(new ReportExceptions("Error generating report"));

    ResponseEntity<Report> response = reportController.generateWeeklyReport(null);

    assertEquals(500, response.getStatusCodeValue());
    assertEquals(null, response.getBody());
    verify(reportService, times(1)).generateReport("weekly", null);
  }

  @Test
  public void testGenerateMonthlyReport_Success() throws Exception {
    Report mockReport = new Report();
    mockReport.setReportType("monthly");
    when(reportService.generateReport("monthly", null)).thenReturn(mockReport);

    ResponseEntity<Report> response = reportController.generateMonthlyReport(null);

    assertEquals(200, response.getStatusCodeValue());
    assertEquals(mockReport, response.getBody());
    verify(reportService, times(1)).generateReport("monthly", null);
  }

  @Test
  public void testGenerateMonthlyReport_Failure() throws Exception {
    when(reportService.generateReport("monthly", null)).thenThrow(new ReportExceptions("Error generating report"));

    ResponseEntity<Report> response = reportController.generateMonthlyReport(null);

    assertEquals(500, response.getStatusCodeValue());
    assertEquals(null, response.getBody());
    verify(reportService, times(1)).generateReport("monthly", null);
  }

  @Test
  public void testGenerateYearlyReport_Success() throws Exception {
    Report mockReport = new Report();
    mockReport.setReportType("yearly");
    when(reportService.generateReport("yearly", null)).thenReturn(mockReport);

    ResponseEntity<Report> response = reportController.generateYearlyReport(null);

    assertEquals(200, response.getStatusCodeValue());
    assertEquals(mockReport, response.getBody());
    verify(reportService, times(1)).generateReport("yearly", null);
  }

  @Test
  public void testGenerateYearlyReport_Failure() throws Exception {
    when(reportService.generateReport("yearly", null)).thenThrow(new ReportExceptions("Error generating report"));

    ResponseEntity<Report> response = reportController.generateYearlyReport(null);

    assertEquals(500, response.getStatusCodeValue());
    assertEquals(null, response.getBody());
    verify(reportService, times(1)).generateReport("yearly", null);
  }

}
