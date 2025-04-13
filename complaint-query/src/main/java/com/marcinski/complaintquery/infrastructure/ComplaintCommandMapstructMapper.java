package com.marcinski.complaintquery.infrastructure;

import com.marcinski.complaintquery.domain.Complaint;
import com.marcinski.complaintquery.domain.event.ComplaintCreatedEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
interface ComplaintCommandMapstructMapper {
    @Mappings({
            @Mapping(target = "reportCounter", constant = "1"),
            @Mapping(target = "creationDate", source = "complaintCreatedEvent.createdDate"),
            @Mapping(target = "country", source = "country")
    })
    Complaint map(ComplaintCreatedEvent complaintCreatedEvent, String country);
}
