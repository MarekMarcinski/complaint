package com.marcinski.complaintcommand.api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EditComplaintRequest {
    @NotBlank(message = "contents field can not be blank")
    private String contents;
}
