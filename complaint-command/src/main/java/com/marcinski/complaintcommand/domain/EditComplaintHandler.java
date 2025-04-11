package com.marcinski.complaintcommand.domain;

import com.marcinski.complaintcommand.domain.command.EditComplaintContentsCommand;
import org.springframework.stereotype.Service;

@Service
class EditComplaintHandler {

    private final ComplaintEventSourcingHandler eventSourcingHandler;

    EditComplaintHandler(ComplaintEventSourcingHandler eventSourcingHandler) {
        this.eventSourcingHandler = eventSourcingHandler;
    }

    void handle(EditComplaintContentsCommand command) {
        var aggregate = eventSourcingHandler.getById(command.getId());
        aggregate.changeContents(command.getContents());
        eventSourcingHandler.save(aggregate);
    }

}
