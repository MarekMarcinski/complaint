package com.marcinski.complaintcommand.domain.command;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class CreateComplaintCommand extends BaseCommand {
    private String reporterName;
    private String contents;
    private String complaintProductId;
    private String ipAddress;
}
