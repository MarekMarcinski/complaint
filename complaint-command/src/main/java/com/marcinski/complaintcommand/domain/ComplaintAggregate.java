package com.marcinski.complaintcommand.domain;

import com.marcinski.complaintcommand.domain.aggregate.AggregateRoot;
import com.marcinski.complaintcommand.domain.command.CreateComplaintCommand;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@NoArgsConstructor
class ComplaintAggregate extends AggregateRoot {
    private UUID complaintProductId;
    private String contents;
    private LocalDate createdDate;
    private String reporterName;
    private String ipAddress;

    ComplaintAggregate(CreateComplaintCommand command) {
        raiseEvent(ComplaintCreatedEvent.builder()
                .id(command.getId())
                .reporterName(command.getReporterName())
                .complaintProductId(UUID.fromString(command.getComplaintProductId()))
                .createdDate(LocalDate.now())
                .ipAddress(command.getIpAddress())
                .contents(command.getContents())
                .build());
    }

    void changeContents(String contents) {
        raiseEvent(ComplaintContentsChangedEvent.builder()
                .id(this.getId())
                .contents(contents)
                .build());
    }

    void apply(ComplaintCreatedEvent event) {
        this.id = event.getId();
        this.complaintProductId = event.getComplaintProductId();
        this.contents = event.getContents();
        this.createdDate = event.getCreatedDate();
        this.reporterName = event.getReporterName();
        this.ipAddress = event.getIpAddress();
    }

    void apply(ComplaintContentsChangedEvent event) {
        this.id = event.getId();
        this.contents = event.getContents();
    }
}