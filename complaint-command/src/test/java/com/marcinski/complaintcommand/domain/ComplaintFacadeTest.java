package com.marcinski.complaintcommand.domain;

import com.marcinski.complaintcommand.TestHelper;
import com.marcinski.complaintcommand.domain.command.CreateComplaintCommand;
import com.marcinski.complaintcommand.domain.command.EditComplaintContentsCommand;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ComplaintFacadeTest {

    @Mock
    private CreateComplaintHandler createComplaintHandler;

    @Mock
    private EditComplaintHandler editComplaintHandler;

    @InjectMocks
    private ComplaintFacade complaintFacade;

    @Test
    public void testHandleCreateComplaintCommand() {
        String productId = "123e4567-e89b-12d3-a456-426614174000";
        String reporterName = "John Doe";
        CreateComplaintCommand command = TestHelper.buildCreateCommand(productId, reporterName);

        UUID expectedId = UUID.nameUUIDFromBytes((reporterName + productId).getBytes());

        String result = complaintFacade.handle(command);

        verify(createComplaintHandler).handle(command);
        assertEquals(expectedId.toString(), result);
    }

    @Test
    public void testHandleEditComplaintContentsCommand() {
        String id = "123e4567-e89b-12d3-a456-426614174000";
        EditComplaintContentsCommand command = TestHelper.buildEditCommand(id);

        String result = complaintFacade.handle(command);

        verify(editComplaintHandler).handle(command);
        assertEquals(id, result);
    }
}
