package com.marcinski.complaintcommand.domain;

import com.marcinski.complaintcommand.domain.command.CreateComplaintCommand;
import com.marcinski.complaintcommand.domain.command.EditComplaintContentsCommand;

import java.util.Date;
import java.util.UUID;

public class TestHelper {

    public static CreateComplaintCommand buildCreateCommand() {
        return buildCreateCommand(UUID.randomUUID().toString(), "John Doe");
    }

    public static CreateComplaintCommand buildCreateCommand(String productId, String reporterName) {
        return CreateComplaintCommand.builder()
                .id(UUID.randomUUID().toString())
                .reporterName(reporterName)
                .complaintProductId(productId)
                .ipAddress("127.0.0.1")
                .contents("Initial complaint content")
                .build();
    }

    public static EditComplaintContentsCommand buildEditCommand() {
        return buildEditCommand(UUID.randomUUID().toString());
    }

    public static EditComplaintContentsCommand buildEditCommand(String id) {
        return EditComplaintContentsCommand.builder()
                .id(id)
                .contents("Initial complaint content")
                .build();
    }

    public static EventModel buildEventModel(BaseEvent event) {
        return EventModel.builder()
                .id(UUID.randomUUID().toString())
                .timestamp(new Date())
                .aggregateIdentifier(UUID.randomUUID().toString())
                .aggregateType("Type")
                .version(1)
                .eventType("eventType")
                .eventData(event)
                .build();
    }
}
