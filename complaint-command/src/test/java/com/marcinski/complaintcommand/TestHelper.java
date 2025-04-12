package com.marcinski.complaintcommand;

import com.marcinski.complaintcommand.api.dto.CreateComplaintRequest;
import com.marcinski.complaintcommand.api.dto.EditComplaintRequest;
import com.marcinski.complaintcommand.domain.BaseEvent;
import com.marcinski.complaintcommand.domain.ComplaintContentsChangedEvent;
import com.marcinski.complaintcommand.domain.ComplaintCreatedEvent;
import com.marcinski.complaintcommand.domain.EventModel;
import com.marcinski.complaintcommand.domain.command.CreateComplaintCommand;
import com.marcinski.complaintcommand.domain.command.EditComplaintContentsCommand;
import com.marcinski.complaintcommand.infrastructure.entity.EventModelDocument;

import java.time.LocalDate;
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

    public static EventModel buildEventEditModel() {
        var event = ComplaintContentsChangedEvent.builder()
                .id(UUID.randomUUID().toString())
                .contents("some string")
                .build();
        return buildEventModel(event);
    }

    public static EventModel buildEventCreateModel() {
        var event = ComplaintCreatedEvent.builder()
                .id(UUID.randomUUID().toString())
                .contents("some string")
                .createdDate(LocalDate.now())
                .reporterName("John Doe")
                .ipAddress("127.0.0.1")
                .build();
        return buildEventModel(event);
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

    public static EventModelDocument buildEventEditModelDocument() {
        var event = ComplaintContentsChangedEvent.builder()
                .id(UUID.randomUUID().toString())
                .contents("some string")
                .build();
        return buildEventModelDocument(event);
    }

    public static EventModelDocument buildEventModelDocument(BaseEvent event) {
        return EventModelDocument.builder()
                .id(UUID.randomUUID().toString())
                .timestamp(new Date())
                .aggregateIdentifier(UUID.randomUUID().toString())
                .aggregateType("Type")
                .version(1)
                .eventType("eventType")
                .eventData(event)
                .build();
    }

    public static EditComplaintRequest buildEditComplaintRequest() {
        var editComplaintRequest = new EditComplaintRequest();
        editComplaintRequest.setContents("Foo");
        return editComplaintRequest;
    }

    public static CreateComplaintRequest buildCreateComplaintRequest() {
        var createComplaintRequest = new CreateComplaintRequest();
        createComplaintRequest.setReporterName("Empik");
        createComplaintRequest.setContents("Bar");
        createComplaintRequest.setComplaintProductId(UUID.randomUUID().toString());
        return createComplaintRequest;
    }
}
