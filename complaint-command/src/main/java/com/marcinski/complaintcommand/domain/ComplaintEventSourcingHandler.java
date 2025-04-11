package com.marcinski.complaintcommand.domain;

import com.marcinski.complaintcommand.domain.aggregate.AggregateRoot;
import org.springframework.stereotype.Service;

import java.util.Comparator;

@Service
class ComplaintEventSourcingHandler {

    private final ComplaintEventStore eventStore;

    ComplaintEventSourcingHandler(ComplaintEventStore eventStore) {
        this.eventStore = eventStore;
    }

    void save(AggregateRoot aggregate) {
        eventStore.saveEvent(aggregate.getId(), aggregate.getUncommittedChanges(), aggregate.getVersion());
        aggregate.markChangesAsCommitted();
    }

    public ComplaintAggregate getById(String id) {
        var aggregate = new ComplaintAggregate();
        var events = eventStore.getEvents(id);
        if (events != null && !events.isEmpty()) {
            aggregate.replayEvents(events);
            var latestVersion = events.stream().map(BaseEvent::getVersion).max(Comparator.naturalOrder());
            aggregate.setVersion(latestVersion.get());
        }
        return aggregate;
    }

}
