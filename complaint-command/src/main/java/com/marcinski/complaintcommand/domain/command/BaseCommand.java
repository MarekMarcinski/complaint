package com.marcinski.complaintcommand.domain.command;


import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public abstract class BaseCommand {
    private String id;
}
