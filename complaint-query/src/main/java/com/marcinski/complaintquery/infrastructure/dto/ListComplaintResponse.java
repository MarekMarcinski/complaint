package com.marcinski.complaintquery.infrastructure.dto;

import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.data.domain.Page;

public record ListComplaintResponse(@JsonValue Page<ComplaintResponse> complaintResponses) {
}
