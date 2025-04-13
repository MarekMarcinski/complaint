package com.marcinski.complaintquery.infrastructure.queryhandler;

import com.marcinski.complaintquery.infrastructure.entity.ComplaintEntity;
import com.marcinski.complaintquery.infrastructure.entity.ComplaintEntityRepository;
import com.marcinski.complaintquery.infrastructure.queryhandler.exception.InvalidSortPropertyException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FindAllQueryHandlerTest {

    @Mock
    private ComplaintEntityRepository complaintRepository;

    @InjectMocks
    private FindAllQueryHandler findAllQueryHandler;

    @Test
    public void testHandleWithValidSortProperty() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("creationDate"));
        ComplaintEntity complaintEntity = new ComplaintEntity();
        List<ComplaintEntity> complaints = Collections.singletonList(complaintEntity);
        Page<ComplaintEntity> page = new PageImpl<>(complaints);

        when(complaintRepository.findAll(pageable)).thenReturn(page);

        FindAllComplaintQuery query = new FindAllComplaintQuery(pageable);
        ListComplaintQueryResponse response = findAllQueryHandler.handle(query);

        assertNotNull(response);
        assertEquals(1, response.getComplaints().getContent().size());
    }

    @Test
    public void testHandleWithInvalidSortProperty() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("invalidProperty"));

        FindAllComplaintQuery query = new FindAllComplaintQuery(pageable);
        assertThrows(InvalidSortPropertyException.class, () -> findAllQueryHandler.handle(query));
    }
}