package com.kirana.register.kirana_store_register.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;

/**
 * Represents a report generated in the system.
 */
@Data
@Document(collection = "report")
public class Report {
  @Id
  private String id; // Unique identifier for the report

  private String reportType; // Type of report

  private double totalCredits; // Total credits in the report

  private double totalDebits; // Total debits in the report

  private double netFlow; // Net flow of the report

  private String currency; // Currency used in the report

  private long timeStamp; // Timestamp of when the report was generated
}
