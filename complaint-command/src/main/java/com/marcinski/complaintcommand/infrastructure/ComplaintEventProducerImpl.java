package com.marcinski.complaintcommand.infrastructure;

import com.marcinski.complaintcommand.domain.BaseEvent;
import com.marcinski.complaintcommand.domain.ComplaintEventProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
class ComplaintEventProducerImpl implements ComplaintEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void produce(String topic, BaseEvent event) {
        log.info("Sending event to kafka {}", event);
        kafkaTemplate.send(topic, event);
    }
}
