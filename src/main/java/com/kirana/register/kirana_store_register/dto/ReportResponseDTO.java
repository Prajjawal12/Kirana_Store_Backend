package com.kirana.register.kirana_store_register.dto;

import lombok.Data;

/**
 * DTO for the response of generated reports.
 */
@Data
public class ReportResponseDTO {
  private String reportType; // Type of report
  private double totalCredits; // Total credits in the report
  private double totalDebits; // Total debits in the report
  private double netFlow; // Net flow of the report
  private String currency; // Currency used in the report
}
