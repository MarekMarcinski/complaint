package com.marcinski.complaintcommand.domain;

import com.marcinski.complaintcommand.domain.command.CreateComplaintCommand;
import org.springframework.stereotype.Component;

@Component
class CreateComplaintHandler {

    private final ComplaintEventSourcingHandler eventSourcingHandler;

    CreateComplaintHandler(ComplaintEventSourcingHandler eventSourcingHandler) {
        this.eventSourcingHandler = eventSourcingHandler;
    }

    void handle(CreateComplaintCommand command) {
        var aggregate = new ComplaintAggregate(command);
        eventSourcingHandler.save(aggregate);
    }
}
