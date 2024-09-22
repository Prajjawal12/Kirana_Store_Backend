package com.kirana.register.kirana_store_register.dto;

import lombok.Data;

@Data
public class ReportResponseDTO {
  private String reportType;
  private double totalCredits;
  private double totalDebits;
  private double netFlow;
  private String currency;
}
