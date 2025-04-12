package com.marcinski.complaintcommand.api;

import com.marcinski.complaintcommand.api.dto.CreateComplaintRequest;
import com.marcinski.complaintcommand.api.dto.EditComplaintRequest;
import com.marcinski.complaintcommand.domain.command.CreateComplaintCommand;
import com.marcinski.complaintcommand.domain.command.EditComplaintContentsCommand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
interface CommandMapper {

    @Mapping(target = "ipAddress", source = "ipAddress")
    CreateComplaintCommand map(CreateComplaintRequest request, String ipAddress);

    @Mapping(target = "id", source = "id")
    EditComplaintContentsCommand map(EditComplaintRequest request, String id);
}
