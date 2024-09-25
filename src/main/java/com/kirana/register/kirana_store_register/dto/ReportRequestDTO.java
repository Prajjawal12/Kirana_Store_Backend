package com.kirana.register.kirana_store_register.dto;

import lombok.Data;

/**
 * DTO for incoming report generation requests.
 */
@Data
public class ReportRequestDTO {
  private String reportType;
  private String currency;
}
