package com.marcinski.complaintcommand.domain;

import com.marcinski.complaintcommand.domain.aggregate.AggregateRoot;
import com.marcinski.complaintcommand.domain.command.CreateComplaintCommand;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.UUID;

@Slf4j
@NoArgsConstructor
class ComplaintAggregate extends AggregateRoot {
    private UUID complaintProductId;
    private String contents;
    private LocalDate createdDate;
    private String reporterName;
    private String ipAddress;

    ComplaintAggregate(CreateComplaintCommand command) {
        log.debug("Raising ComplaintCreatedEvent for Command: {}", command);
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
        log.debug("Raising ComplaintContentsChangedEvent for contents: {}", contents);
        raiseEvent(ComplaintContentsChangedEvent.builder()
                .id(this.getId())
                .contents(contents)
                .build());
    }

    void apply(ComplaintCreatedEvent event) {
        log.debug("Applying changes for event: {}", event);
        this.id = event.getId();
        this.complaintProductId = event.getComplaintProductId();
        this.contents = event.getContents();
        this.createdDate = event.getCreatedDate();
        this.reporterName = event.getReporterName();
        this.ipAddress = event.getIpAddress();
    }

    void apply(ComplaintContentsChangedEvent event) {
        log.debug("Applying changes for event: {}", event);
        this.id = event.getId();
        this.contents = event.getContents();
    }
}