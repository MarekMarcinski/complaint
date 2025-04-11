package com.marcinski.complaintcommand.domain;

import com.marcinski.complaintcommand.domain.aggregate.AggregateRoot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ComplaintEventSourcingHandlerTest {

    @Mock
    private ComplaintEventStore eventStore;

    @InjectMocks
    private ComplaintEventSourcingHandler complaintEventSourcingHandler;

    private AggregateRoot aggregateRoot;

    @BeforeEach
    public void setUp() {
        aggregateRoot = new ComplaintAggregate();
    }

    @Test
    public void testSaveAggregateRoot() {
        String id = aggregateRoot.getId();
        List<BaseEvent> uncommittedChanges = aggregateRoot.getUncommittedChanges();
        int version = aggregateRoot.getVersion();

        complaintEventSourcingHandler.save(aggregateRoot);

        verify(eventStore).saveEvent(id, uncommittedChanges, version);
        assertTrue(aggregateRoot.getUncommittedChanges().isEmpty());
    }
}