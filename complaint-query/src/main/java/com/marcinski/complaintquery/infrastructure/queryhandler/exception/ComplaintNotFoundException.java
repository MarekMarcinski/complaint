package com.marcinski.complaintquery.infrastructure.queryhandler.exception;

public class ComplaintNotFoundException extends RuntimeException {
    public ComplaintNotFoundException(String id) {
        super("Complaint with id " + id + " not found");
    }
}
