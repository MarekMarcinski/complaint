package com.marcinski.complaintcommand.infrastructure;

import com.marcinski.complaintcommand.domain.EventModel;
import com.marcinski.complaintcommand.domain.EventRepository;
import com.marcinski.complaintcommand.infrastructure.entity.EventModelDocument;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
class EventRepositoryImpl implements EventRepository {

    private final EventStoreRepository eventStoreRepository;
    private final EventModelMapper eventModelMapper;

    @Override
    public List<EventModel> findByAggregateIdentifier(String aggregateIdentifier) {
        List<EventModelDocument> byAggregateIdentifier = eventStoreRepository.findByAggregateIdentifier(aggregateIdentifier);
        return byAggregateIdentifier.stream().map(eventModelMapper::map).collect(Collectors.toList());
    }

    @Override
    public EventModel save(EventModel eventModel) {
        EventModelDocument eventModelDocument = eventModelMapper.map(eventModel);
        log.debug("Saving event: {}", eventModelDocument);
        EventModelDocument save = eventStoreRepository.save(eventModelDocument);
        return eventModelMapper.map(save);
    }
}