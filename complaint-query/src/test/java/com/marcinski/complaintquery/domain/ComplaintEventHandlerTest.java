package com.marcinski.complaintquery.domain;

import com.marcinski.complaintquery.domain.event.ComplaintContentsChangedEvent;
import com.marcinski.complaintquery.domain.event.ComplaintCreatedEvent;
import com.marcinski.complaintquery.domain.geo.CityLocator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static com.marcinski.complaintquery.TestHelper.buildComplaintCreatedEvent;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ComplaintEventHandlerTest {

    @Mock
    private ComplaintRepository complaintRepository;

    @Mock
    private ComplaintCommandMapper complaintCommandMapper;

    @Mock
    private CityLocator cityLocator;

    @InjectMocks
    private ComplaintEventHandler complaintEventHandler;

    @Test
    void testOnComplaintCreatedEvent_NewComplaint() {
        ComplaintCreatedEvent event = buildComplaintCreatedEvent();

        String country = "POLAND";
        when(cityLocator.getCityByIp(event.getIpAddress())).thenReturn(country);
        Complaint mappedComplaint = new Complaint();
        when(complaintCommandMapper.map(event, country)).thenReturn(mappedComplaint);

        complaintEventHandler.on(event);

        verify(complaintRepository).save(mappedComplaint);
    }

    @Test
    void testOnComplaintCreatedEvent_ExistingComplaint() {
        Complaint existingComplaint = new Complaint();
        existingComplaint.setReportCounter(1);
        UUID id = UUID.randomUUID();
        existingComplaint.setId(id);

        when(complaintRepository.findById(any(UUID.class))).thenReturn(Optional.of(existingComplaint));

        ComplaintCreatedEvent event = buildComplaintCreatedEvent(UUID.randomUUID());

        complaintEventHandler.on(event);

        assertEquals(2, existingComplaint.getReportCounter());
        verify(complaintRepository).save(existingComplaint);
    }

    @Test
    void testOnComplaintContentsChangedEvent() {
        Complaint existingComplaint = new Complaint();
        UUID id = UUID.randomUUID();
        existingComplaint.setId(id);

        when(complaintRepository.findById(id)).thenReturn(Optional.of(existingComplaint));

        ComplaintContentsChangedEvent event = ComplaintContentsChangedEvent.builder()
                .id(id.toString())
                .contents("New complaint")
                .build();

        complaintEventHandler.on(event);

        assertEquals("New complaint", existingComplaint.getContents());
        verify(complaintRepository).save(existingComplaint);
    }
}