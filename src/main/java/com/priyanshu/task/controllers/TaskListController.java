package com.priyanshu.task.controllers;

import com.priyanshu.task.domain.dto.TaskListDTO;
import com.priyanshu.task.domain.entities.TaskList;
import com.priyanshu.task.mappers.impl.TaskListMapper;
import com.priyanshu.task.services.TaskListService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "/task-lists")
public class TaskListController {

    private TaskListService taskListService;
    private TaskListMapper taskListMapper;

    public TaskListController(TaskListService taskListService, TaskListMapper taskListMapper) {
        this.taskListService = taskListService;
        this.taskListMapper = taskListMapper;
    }

    @GetMapping
    public List<TaskListDTO> listTaskLists(){
        return taskListService.listTaskLists()
                .stream()
                .map(taskListMapper::toDto)
                .toList();
    }

    @PostMapping
    public TaskListDTO createTaskList(@RequestBody TaskListDTO taskListDTO){
        TaskList taskList = taskListService.createTaskList(taskListMapper.fromDto(taskListDTO));
        return taskListMapper.toDto(taskList);
    }

    @GetMapping("/{task_list_id}")
    public Optional<TaskListDTO> getTaskList(@PathVariable("task_list_id") UUID taskListId){
        return taskListService.getTaskList(taskListId)
                .map(taskListMapper::toDto);
    }

    @PutMapping("/{task_list_id}")
    public TaskListDTO update(@PathVariable("task_list_id") UUID taskListId, @RequestBody TaskListDTO taskListDTO){
        TaskList updatedList = taskListService.updateTaskList(taskListId, taskListMapper.fromDto(taskListDTO));
        return taskListMapper.toDto(updatedList);
    }

    @DeleteMapping("/{task_list_id}")
    public ResponseEntity<Void> deleteTaskList(@PathVariable("task_list_id") UUID taskListId){
        taskListService.deleteTaskList(taskListId);
        return ResponseEntity.noContent().build();
    }
}
