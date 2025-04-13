package com.marcinski.complaintquery.infrastructure.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.marcinski.complaintquery.domain.event.ComplaintContentsChangedEvent;
import com.marcinski.complaintquery.domain.event.ComplaintCreatedEvent;
import org.apache.kafka.common.serialization.Deserializer;

public class MultiTypeKafkaDeserializer implements Deserializer<Object> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Object deserialize(String topic, byte[] data) {
        try {
            JsonNode root = objectMapper.readTree(data);


            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            Class<?> targetClass = switch (topic) {
                case "ComplaintCreatedEvent" -> ComplaintCreatedEvent.class;
                case "ComplaintContentsChangedEvent" -> ComplaintContentsChangedEvent.class;
                default -> throw new IllegalArgumentException("Unknown type: " + topic);
            };

            return objectMapper.treeToValue(root, targetClass);

        } catch (Exception e) {
            throw new RuntimeException("Error deserializing message", e);
        }
    }
}