package com.marcinski.complaintcommand.domain;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
class ComplaintContentsChangedEvent extends BaseEvent {
    private String contents;
}
