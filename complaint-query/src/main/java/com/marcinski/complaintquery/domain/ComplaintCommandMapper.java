package com.marcinski.complaintquery.domain;

import com.marcinski.complaintquery.domain.event.ComplaintCreatedEvent;

public interface ComplaintCommandMapper {
    Complaint map(ComplaintCreatedEvent complaintCreatedEvent, String country);
}
