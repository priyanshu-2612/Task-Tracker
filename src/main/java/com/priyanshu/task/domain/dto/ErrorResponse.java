package com.priyanshu.task.domain.dto;

public record ErrorResponse(
        int status,
        String message,
        String description
) {
}
