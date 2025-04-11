package com.marcinski.complaintcommand.domain;


import java.util.List;

public interface EventRepository {
    List<EventModel> findByAggregateIdentifier(String aggregateIdentifier);

    EventModel save(EventModel eventModel);
}
