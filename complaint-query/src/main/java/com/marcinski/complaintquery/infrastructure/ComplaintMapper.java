package com.marcinski.complaintquery.infrastructure;

import com.marcinski.complaintquery.domain.Complaint;
import com.marcinski.complaintquery.infrastructure.dto.ComplaintResponse;
import com.marcinski.complaintquery.infrastructure.entity.ComplaintEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
interface ComplaintMapper {
    ComplaintResponse map(ComplaintEntity complaint);

    ComplaintEntity map(Complaint complaint);

    Complaint mapToDomain(ComplaintEntity complaint);
}
