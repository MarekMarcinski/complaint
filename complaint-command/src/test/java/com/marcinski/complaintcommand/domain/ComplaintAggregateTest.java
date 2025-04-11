package com.marcinski.complaintcommand.domain;

import com.marcinski.complaintcommand.domain.command.CreateComplaintCommand;
import com.marcinski.complaintcommand.domain.command.EditComplaintContentsCommand;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static com.marcinski.complaintcommand.TestHelper.buildCreateCommand;
import static com.marcinski.complaintcommand.TestHelper.buildEditCommand;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ComplaintAggregateTest {
    @Test
    public void testComplaintCreation() {
        CreateComplaintCommand command = buildCreateCommand();

        ComplaintAggregate aggregate = new ComplaintAggregate(command);

        assertEquals(1, aggregate.getUncommittedChanges().size());
        ComplaintCreatedEvent event = (ComplaintCreatedEvent) aggregate.getUncommittedChanges().get(0);
        assertEquals(command.getId(), event.getId());
        assertEquals(command.getReporterName(), event.getReporterName());
        assertEquals(UUID.fromString(command.getComplaintProductId()), event.getComplaintProductId());
        assertEquals(LocalDate.now(), event.getCreatedDate());
        assertEquals(command.getIpAddress(), event.getIpAddress());
        assertEquals(command.getContents(), event.getContents());
    }

    @Test
    public void testChangeContents() {
        CreateComplaintCommand createCommand = buildCreateCommand();

        ComplaintAggregate aggregate = new ComplaintAggregate(createCommand);

        EditComplaintContentsCommand editCommand = buildEditCommand(aggregate.getId());

        aggregate.changeContents(editCommand.getContents());

        assertEquals(2, aggregate.getUncommittedChanges().size());
        ComplaintContentsChangedEvent event = (ComplaintContentsChangedEvent) aggregate.getUncommittedChanges().get(1);
        assertEquals(editCommand.getId(), event.getId());
        assertEquals(editCommand.getContents(), event.getContents());
    }

}