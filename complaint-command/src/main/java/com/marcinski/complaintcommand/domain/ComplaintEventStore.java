package com.marcinski.complaintcommand.domain;


import com.marcinski.complaintcommand.domain.exceptions.AggregateNotFoundException;
import com.marcinski.complaintcommand.domain.exceptions.ConcurrencyException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
class ComplaintEventStore {

    private final ComplaintEventProducer eventProducer;
    private final EventRepository eventStoreRepository;

    ComplaintEventStore(ComplaintEventProducer eventProducer, EventRepository eventStoreRepository) {
        this.eventProducer = eventProducer;
        this.eventStoreRepository = eventStoreRepository;
    }

    void saveEvent(String aggregateId, Iterable<BaseEvent> events, int expectedVersion) {
        var eventStream = eventStoreRepository.findByAggregateIdentifier(aggregateId);
        if (expectedVersion != -1 && eventStream.get(eventStream.size() - 1).getVersion() != expectedVersion) {
            throw new ConcurrencyException("");
        }
        var version = expectedVersion;
        for (var event : events) {
            version++;
            event.setVersion(version);
            var eventModel = EventModel.builder()
                    .timestamp(new Date())
                    .aggregateIdentifier(aggregateId)
                    .eventType(event.getClass().getTypeName())
                    .aggregateType(ComplaintAggregate.class.getTypeName())
                    .eventData(event)
                    .version(version)
                    .build();
            var persistedEvent = eventStoreRepository.save(eventModel);
            if (!persistedEvent.getId().isEmpty()) {
                eventProducer.produce(event.getClass().getSimpleName(), event);
            }
        }
    }

    List<BaseEvent> getEvents(String aggregateId) {
        var eventStream = eventStoreRepository.findByAggregateIdentifier(aggregateId);
        if (eventStream == null || eventStream.isEmpty()) {
            throw new AggregateNotFoundException(aggregateId);
        }
        return eventStream.stream().map(EventModel::getEventData).collect(Collectors.toList());
    }
}
