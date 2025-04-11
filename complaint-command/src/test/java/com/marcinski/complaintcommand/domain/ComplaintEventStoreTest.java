package com.marcinski.complaintcommand.domain;

import com.marcinski.complaintcommand.TestHelper;
import com.marcinski.complaintcommand.domain.exceptions.AggregateNotFoundException;
import com.marcinski.complaintcommand.domain.exceptions.ConcurrencyException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ComplaintEventStoreTest {

    @Mock
    private ComplaintEventProducer eventProducer;

    @Mock
    private EventRepository eventStoreRepository;

    @InjectMocks
    private ComplaintEventStore complaintEventStore;

    private String aggregateId;
    private List<BaseEvent> events;

    @BeforeEach
    public void setUp() {
        aggregateId = "123e4567-e89b-12d3-a456-426614174000";
        events = Collections.singletonList(ComplaintContentsChangedEvent.builder().build());
    }

    @Test
    public void testSaveEvent() {
        int expectedVersion = 1;
        when(eventStoreRepository.findByAggregateIdentifier(aggregateId)).thenReturn(List.of(TestHelper.buildEventModel(null)));
        when(eventStoreRepository.save(any(EventModel.class))).thenReturn(TestHelper.buildEventModel(null));

        complaintEventStore.saveEvent(aggregateId, events, expectedVersion);

        verify(eventStoreRepository).save(any(EventModel.class));
        verify(eventProducer).produce(anyString(), any(BaseEvent.class));
    }

    @Test
    public void testSaveEventConcurrencyException() {
        int expectedVersion = 0;
        var eventStream = List.of(TestHelper.buildEventModel(null));
        when(eventStoreRepository.findByAggregateIdentifier(aggregateId)).thenReturn(eventStream);

        assertThrows(ConcurrencyException.class, () -> {
            complaintEventStore.saveEvent(aggregateId, events, expectedVersion);
        });
    }

    @Test
    public void testGetEventsAggregateNotFoundException() {
        when(eventStoreRepository.findByAggregateIdentifier(aggregateId)).thenReturn(Collections.emptyList());

        assertThrows(AggregateNotFoundException.class, () -> complaintEventStore.getEvents(aggregateId));
    }
}