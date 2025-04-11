package com.marcinski.complaintcommand.domain;

import com.marcinski.complaintcommand.domain.command.EditComplaintContentsCommand;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static com.marcinski.complaintcommand.domain.TestHelper.buildEditCommand;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EditComplaintHandlerTest {

    @Mock
    private ComplaintEventSourcingHandler eventSourcingHandler;

    @InjectMocks
    private EditComplaintHandler editComplaintHandler;

    @Test
    public void testHandleCommand() {
        String id = UUID.randomUUID().toString();
        String contents = "Initial complaint content";
        EditComplaintContentsCommand command = buildEditCommand(id);

        ComplaintAggregate aggregate = mock(ComplaintAggregate.class);
        when(eventSourcingHandler.getById(id)).thenReturn(aggregate);

        editComplaintHandler.handle(command);

        verify(aggregate).changeContents(contents);
        verify(eventSourcingHandler).save(aggregate);
    }
}