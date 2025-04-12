package com.marcinski.complaintquery.domain;

import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class Complaint {

    private UUID id;
    private UUID complaintProductId;
    private String reporterName;
    private String contents;
    private LocalDate creationDate;
    private String country;
    private Integer reportCounter;

    public void incrementCounter() {
        if (this.reportCounter == null) {
            this.reportCounter = 1;
        }
        this.reportCounter++;
    }
}
