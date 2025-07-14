package com.priyanshu.task.domain.dto;

import com.priyanshu.task.domain.entities.TaskPriority;
import com.priyanshu.task.domain.entities.TaskStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record TaskDTO(
        UUID id,
        String title,
        String description,
        LocalDateTime dueDate,
        TaskPriority priority,
        TaskStatus status
) {
}
