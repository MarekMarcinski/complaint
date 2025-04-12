package com.marcinski.complaintcommand.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
public class ComplaintContentsChangedEvent extends BaseEvent {
    private String contents;
}
