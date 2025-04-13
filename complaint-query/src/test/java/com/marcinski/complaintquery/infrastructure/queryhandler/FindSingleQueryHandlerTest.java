package com.marcinski.complaintquery.infrastructure.queryhandler;

import com.marcinski.complaintquery.infrastructure.entity.ComplaintEntity;
import com.marcinski.complaintquery.infrastructure.entity.ComplaintEntityRepository;
import com.marcinski.complaintquery.infrastructure.queryhandler.exception.ComplaintNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FindSingleQueryHandlerTest {

    @Mock
    private ComplaintEntityRepository complaintRepository;

    @InjectMocks
    private FindSingleQueryHandler findSingleQueryHandler;


    @Test
    public void testHandleWithValidComplaintId() {
        UUID validId = UUID.randomUUID();
        ComplaintEntity complaintEntity = new ComplaintEntity();
        when(complaintRepository.findById(validId)).thenReturn(Optional.of(complaintEntity));

        FindComplaintByIdQuery query = new FindComplaintByIdQuery(validId.toString());
        SingleComplaintQueryResponse response = findSingleQueryHandler.handle(query);

        assertNotNull(response);
        assertEquals(complaintEntity, response.getComplaint());
    }

    @Test
    public void testHandleWithInvalidComplaintId() {
        UUID invalidId = UUID.randomUUID();
        when(complaintRepository.findById(invalidId)).thenReturn(Optional.empty());

        FindComplaintByIdQuery query = new FindComplaintByIdQuery(invalidId.toString());
        assertThrows(ComplaintNotFoundException.class, () -> findSingleQueryHandler.handle(query));
    }
}