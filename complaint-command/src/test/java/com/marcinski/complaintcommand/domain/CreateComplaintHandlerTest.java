package com.marcinski.complaintcommand.domain;

import com.marcinski.complaintcommand.domain.command.CreateComplaintCommand;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.marcinski.complaintcommand.domain.TestHelper.buildCreateCommand;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CreateComplaintHandlerTest {

    @Mock
    private ComplaintEventSourcingHandler eventSourcingHandler;

    @InjectMocks
    private CreateComplaintHandler createComplaintHandler;

    @Test
    public void testHandleCommand() {
        CreateComplaintCommand command = buildCreateCommand();

        createComplaintHandler.handle(command);

        verify(eventSourcingHandler).save(any(ComplaintAggregate.class));
    }
}
