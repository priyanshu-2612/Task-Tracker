package com.priyanshu.task.mappers.impl;

import com.priyanshu.task.domain.dto.TaskDTO;
import com.priyanshu.task.domain.entities.Task;
import com.priyanshu.task.mappers.Mapper;
import org.springframework.stereotype.Component;

@Component
public class TaskMapperImpl implements Mapper<Task, TaskDTO> {
    @Override
    public Task fromDto(TaskDTO taskDto) {
        return new Task(taskDto.id(), taskDto.title(), taskDto.description(), taskDto.dueDate(), taskDto.status(), taskDto.priority(), null, null, null);
    }

    @Override
    public TaskDTO toDto(Task task) {
        return new TaskDTO(task.getId(), task.getTitle(), task.getDescription(), task.getDueDate(), task.getPriority(), task.getStatus());
    }
}
