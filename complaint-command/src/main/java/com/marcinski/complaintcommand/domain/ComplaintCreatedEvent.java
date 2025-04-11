package com.marcinski.complaintcommand.domain;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@SuperBuilder
class ComplaintCreatedEvent extends BaseEvent {
    private UUID complaintProductId;
    private String contents;
    private LocalDate createdDate;
    private String reporterName;
    private String ipAddress;
}
