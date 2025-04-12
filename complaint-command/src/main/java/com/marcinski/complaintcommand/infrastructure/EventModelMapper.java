package com.marcinski.complaintcommand.infrastructure;

import com.marcinski.complaintcommand.domain.EventModel;
import com.marcinski.complaintcommand.infrastructure.entity.EventModelDocument;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EventModelMapper {

    EventModelDocument map (EventModel eventModel);
    EventModel map (EventModelDocument eventModelDocument);
}
