package com.marcinski.complaintcommand.api.dto;

import com.marcinski.complaintcommand.api.validation.UUIDSyntax;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateComplaintRequest {
    @NotBlank(message = "reporterName field can not be blank")
    private String reporterName;
    @NotBlank(message = "contents field can not be blank")
    private String contents;
    @NotNull(message = "complaintProductId can not be empty")
    @UUIDSyntax(message = "complaintProductId must be an UUID")
    private String complaintProductId;
}
