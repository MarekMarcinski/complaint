package com.marcinski.complaintcommand.infrastructure;


import com.marcinski.complaintcommand.infrastructure.entity.EventModelDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface EventStoreRepository extends MongoRepository<EventModelDocument, String> {
    List<EventModelDocument> findByAggregateIdentifier(String aggregateIdentifier);
}
