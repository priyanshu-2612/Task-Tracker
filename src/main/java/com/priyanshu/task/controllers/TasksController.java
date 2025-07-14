package com.priyanshu.task.controllers;

import com.priyanshu.task.domain.dto.TaskDTO;
import com.priyanshu.task.domain.entities.Task;
import com.priyanshu.task.mappers.Mapper;
import com.priyanshu.task.services.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/task-lists/{task_list_id}/tasks")
public class TasksController {

    private final TaskService taskService;
    private final Mapper<Task, TaskDTO> taskMapper;

    public TasksController(TaskService taskService, Mapper<Task, TaskDTO> taskMapper) {
        this.taskService = taskService;
        this.taskMapper = taskMapper;
    }

    @GetMapping
    public List<TaskDTO> listTasks(@PathVariable("task_list_id") UUID taskListId){
        return taskService.listTasks(taskListId)
                .stream()
                .map(taskMapper::toDto)
                .toList();
    }

    @PostMapping
    public TaskDTO createTask(@PathVariable("task_list_id") UUID taskListId,
                              @RequestBody TaskDTO taskDTO){
        Task createdTask = taskService.createTask(taskListId, taskMapper.fromDto(taskDTO));
        return taskMapper.toDto(createdTask);
    }

    @GetMapping("/{task_id}")
    public Optional<TaskDTO> getTask(@PathVariable("task_list_id") UUID taskListId, @PathVariable("task_id") UUID taskId){
        return taskService.getTask(taskListId, taskId).map(taskMapper::toDto);
    }

    @PutMapping("/{task_id}")
    public TaskDTO updateTask(@PathVariable("task_list_id") UUID taskListId,
                              @PathVariable("/task_id") UUID taskId,
                              @RequestBody TaskDTO taskDTO){
        Task updatedTask = taskService.updateTask(taskListId, taskId, taskMapper.fromDto(taskDTO));
        return taskMapper.toDto(updatedTask);
    }

    @DeleteMapping("/{task_id}")
    public ResponseEntity<Void> deleteTask(@PathVariable("task_list_id") UUID taskListId,
                                     @PathVariable("task_id") UUID taskId){
        taskService.deleteTask(taskListId, taskId);
        return ResponseEntity.noContent().build();
    }
}
