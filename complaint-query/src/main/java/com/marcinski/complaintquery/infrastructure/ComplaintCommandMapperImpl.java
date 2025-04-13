package com.marcinski.complaintquery.infrastructure;

import com.marcinski.complaintquery.domain.Complaint;
import com.marcinski.complaintquery.domain.ComplaintCommandMapper;
import com.marcinski.complaintquery.domain.event.ComplaintCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class ComplaintCommandMapperImpl implements ComplaintCommandMapper {

    private final ComplaintCommandMapstructMapper complaintCommandMapstructMapper;

    @Override
    public Complaint map(ComplaintCreatedEvent complaintCreatedEvent, String country) {
        return complaintCommandMapstructMapper.map(complaintCreatedEvent, country);
    }
}
