package com.marcinski.complaintcommand.infrastructure;

import com.marcinski.complaintcommand.domain.BaseEvent;
import com.marcinski.complaintcommand.domain.ComplaintEventProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class ComplaintEventProducerImpl implements ComplaintEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void produce(String topic, BaseEvent event) {
        kafkaTemplate.send(topic, event);
    }
}
