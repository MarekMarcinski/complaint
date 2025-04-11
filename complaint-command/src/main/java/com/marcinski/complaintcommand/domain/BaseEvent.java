package com.marcinski.complaintcommand.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public abstract class BaseEvent {
    @Setter
    private int version;
    private String id;
}
