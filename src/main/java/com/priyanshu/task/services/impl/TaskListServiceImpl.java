package com.priyanshu.task.services.impl;

import com.priyanshu.task.domain.entities.TaskList;
import com.priyanshu.task.repositories.TaskListRepository;
import com.priyanshu.task.services.TaskListService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskListServiceImpl implements TaskListService {

    private TaskListRepository taskListRepository;

    public TaskListServiceImpl(TaskListRepository taskListRepository) {
        this.taskListRepository = taskListRepository;
    }

    @Override
    public List<TaskList> listTaskLists() {
        return taskListRepository.findAll();
    }

    @Override
    public TaskList createTaskList(TaskList taskList) {
        if(taskList.getId() != null){
            throw new IllegalArgumentException("Task list already has an id");
        }
        if(taskList.getTitle()==null || taskList.getTitle().isBlank()){
            throw new IllegalArgumentException("Task list title must be present");
        }
        LocalDateTime now = LocalDateTime.now();
        return taskListRepository.save(new TaskList(
                null,
                taskList.getTitle(),
                taskList.getDescription(),
                null,
                now,
                now
        ));
    }

    @Override
    public Optional<TaskList> getTaskList(UUID id) {
        return taskListRepository.findById(id);
    }

    @Override
    @Transactional
    public TaskList updateTaskList(UUID id, TaskList taskList) {
        if(taskList.getId() == null){
            throw new IllegalArgumentException("Task list must have an id");
        }
        if(!taskList.getId().equals(id)){
            throw new IllegalArgumentException("Attempting to change task list id which is not permitted");
        }
        if(!taskListRepository.existsById(id)){
            throw new IllegalArgumentException("Task list not found");
        }
        TaskList existingList = taskListRepository.getById(id);
        existingList.setTitle(taskList.getTitle());
        existingList.setDescription(taskList.getDescription());
        existingList.setUpdated(LocalDateTime.now());
        return taskListRepository.save(taskList);
    }

    @Override
    public void deleteTaskList(UUID id) {
        taskListRepository.deleteById(id);
    }
}
