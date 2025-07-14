package com.priyanshu.task.mappers.impl;

import com.priyanshu.task.domain.dto.TaskDTO;
import com.priyanshu.task.domain.dto.TaskListDTO;
import com.priyanshu.task.domain.entities.Task;
import com.priyanshu.task.domain.entities.TaskList;
import com.priyanshu.task.domain.entities.TaskStatus;
import com.priyanshu.task.mappers.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.List;


@Component
public class TaskListMapper implements Mapper<TaskList, TaskListDTO> {

    @Autowired
    private final Mapper<Task, TaskDTO> taskmapper;

    public TaskListMapper(Mapper<Task, TaskDTO> taskmapper) {
        this.taskmapper = taskmapper;
    }

    @Override
    public TaskList fromDto(TaskListDTO taskListDTO) {
        return new TaskList(taskListDTO.id(),
                            taskListDTO.title(),
                            taskListDTO.description(),
                            Optional.ofNullable(taskListDTO.tasks())
                                    .map(tasks -> tasks.stream().map(taskmapper::fromDto)
                                            .toList()
                                    ).orElse(null),
                            null,
                            null
        );
    }

    @Override
    public TaskListDTO toDto(TaskList taskList) {
        return new TaskListDTO(taskList.getId(),
                taskList.getTitle(),
                taskList.getDescription(),
                Optional.ofNullable(taskList.getTasks())
                        .map(List::size)
                        .orElse(0),
                calculateTaskListProgress(taskList.getTasks()),
                Optional.ofNullable(taskList.getTasks())
                        .map(tasks -> tasks.stream().map(taskmapper::toDto)
                                .toList()
                        ).orElse(null)
                );
    }

    private Double calculateTaskListProgress(List<Task> tasks){
        if(tasks == null || tasks.isEmpty()){
            return null;
        }
        long closedTaskCount = tasks.stream().filter(task -> TaskStatus.CLOSED == task.getStatus())
                .count();
       return (double) (closedTaskCount/tasks.size());
    }
}
