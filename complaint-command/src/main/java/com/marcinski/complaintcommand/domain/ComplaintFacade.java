package com.marcinski.complaintcommand.domain;

import com.marcinski.complaintcommand.domain.command.CreateComplaintCommand;
import com.marcinski.complaintcommand.domain.command.EditComplaintContentsCommand;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ComplaintFacade {

    private final CreateComplaintHandler createComplaintHandler;
    private final EditComplaintHandler editComplaintHandler;

    public ComplaintFacade(CreateComplaintHandler createComplaintHandler, EditComplaintHandler editComplaintHandler) {
        this.createComplaintHandler = createComplaintHandler;
        this.editComplaintHandler = editComplaintHandler;
    }


    public String handle(CreateComplaintCommand command) {
        var productId = command.getComplaintProductId();
        var reporterName = command.getReporterName();
        var id = UUID.nameUUIDFromBytes((reporterName + productId).getBytes());
        command.setId(id.toString());
        createComplaintHandler.handle(command);
        return id.toString();
    }

    public String handle(EditComplaintContentsCommand command) {
        String id = command.getId();
        editComplaintHandler.handle(command);
        return id;
    }
}
