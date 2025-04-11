package com.marcinski.complaintcommand.domain.exceptions;

public class AggregateNotFoundException extends DomainException {

    public AggregateNotFoundException(String id) {
        super("Aggregate not found for id " + id);
    }
}
