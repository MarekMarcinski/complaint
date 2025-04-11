package com.marcinski.complaintcommand.domain;


public interface ComplaintEventProducer {

    void produce(String key, BaseEvent event);
}
