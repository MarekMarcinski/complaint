package com.marcinski.complaintcommand.domain.command;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class EditComplaintContentsCommand extends BaseCommand {
    private String contents;
}
