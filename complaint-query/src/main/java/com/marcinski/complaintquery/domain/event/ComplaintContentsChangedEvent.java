package com.marcinski.complaintquery.domain.event;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
public class ComplaintContentsChangedEvent extends BaseEvent {
    private String contents;
}
