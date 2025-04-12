package com.marcinski.complaintcommand.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record ApiError(String errorMessage, List<String> errors) {

    public static ApiError of(String message) {
        return new ApiError(message, null);
    }

    public static ApiError of(String message, List<String> errors) {
        return new ApiError(message, errors);
    }
}
