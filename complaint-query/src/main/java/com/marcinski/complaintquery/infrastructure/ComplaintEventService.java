package com.marcinski.complaintquery.infrastructure;

import com.marcinski.complaintquery.domain.ComplaintEventHandler;
import com.marcinski.complaintquery.domain.event.ComplaintContentsChangedEvent;
import com.marcinski.complaintquery.domain.event.ComplaintCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
class ComplaintEventService {

    private final ComplaintEventHandler eventHandler;

    @KafkaListener(topics = "ComplaintCreatedEvent", groupId = "${spring.kafka.consumer.group-id}")
    @Transactional
    public void consume(ComplaintCreatedEvent event, Acknowledgment ack) {
        eventHandler.on(event);
        ack.acknowledge();
    }

    @KafkaListener(topics = "ComplaintContentsChangedEvent", groupId = "${spring.kafka.consumer.group-id}")
    @Transactional
    public void consume(ComplaintContentsChangedEvent event, Acknowledgment ack) {
        eventHandler.on(event);
        ack.acknowledge();
    }
}
