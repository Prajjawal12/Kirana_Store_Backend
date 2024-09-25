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
  private String id; 

  private String reportType; 

  private double totalCredits; 

  private double totalDebits;

  private double netFlow; 

  private String currency; 

  private long timeStamp; 
}
