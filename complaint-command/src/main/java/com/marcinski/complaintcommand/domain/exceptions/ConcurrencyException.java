package com.marcinski.complaintcommand.domain.exceptions;

public class ConcurrencyException extends DomainException {
    public ConcurrencyException(String message) {
        super(message);
    }
}
