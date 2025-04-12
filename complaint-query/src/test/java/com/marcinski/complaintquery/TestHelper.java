package com.marcinski.complaintquery;

import com.marcinski.complaintquery.domain.event.ComplaintCreatedEvent;

import java.time.LocalDate;
import java.util.UUID;

public class TestHelper {

    public static ComplaintCreatedEvent buildComplaintCreatedEvent() {
        return buildComplaintCreatedEvent(UUID.randomUUID());
    }

    public static ComplaintCreatedEvent buildComplaintCreatedEvent(UUID id) {
        return ComplaintCreatedEvent.builder()
                .id(id.toString())
                .complaintProductId(UUID.randomUUID())
                .contents("Contents")
                .createdDate(LocalDate.now())
                .reporterName("Marek")
                .ipAddress("127.0.0.1")
                .build();
    }
}
