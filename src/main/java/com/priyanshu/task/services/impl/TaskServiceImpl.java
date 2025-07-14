package com.priyanshu.task.services.impl;

import com.priyanshu.task.domain.entities.Task;
import com.priyanshu.task.domain.entities.TaskList;
import com.priyanshu.task.domain.entities.TaskPriority;
import com.priyanshu.task.domain.entities.TaskStatus;
import com.priyanshu.task.repositories.TaskListRepository;
import com.priyanshu.task.repositories.TaskRepository;
import com.priyanshu.task.services.TaskService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskListRepository taskListRepository;

    public TaskServiceImpl(TaskRepository taskRepository, TaskListRepository taskListRepository) {
        this.taskRepository = taskRepository;
        this.taskListRepository = taskListRepository;
    }

    @Override
    public List<Task> listTasks(UUID taskListId) {
        return taskRepository.findByTaskListId(taskListId);
    }

    @Override
    @Transactional
    public Task createTask(UUID taskListId, Task task) {
        if(task.getId()!=null){
            throw new IllegalArgumentException("Task already has an id");
        }
        if(task.getTitle()==null || task.getTitle().isBlank()){
            throw new IllegalArgumentException("Task must have a title");
        }

        TaskPriority taskPrioriy = Optional.ofNullable(task.getPriority())
                .orElse(TaskPriority.MEDIUM);

        TaskStatus taskStatus = TaskStatus.OPEN;

        TaskList taskList = taskListRepository.findById(taskListId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Task List Id provided"));

        LocalDateTime now = LocalDateTime.now();
        Task taskToSave = new Task(null,
                task.getTitle(),
                task.getDescription(),
                task.getDueDate(),
                taskStatus,
                taskPrioriy,
                taskList,
                now,
                now);
        return taskRepository.save(taskToSave);
    }

    @Override
    public Optional<Task> getTask(UUID taskListId, UUID taskId) {
        return taskRepository.findByTaskListIdAndId(taskListId, taskId);
    }

    @Override
    @Transactional
    public Task updateTask(UUID taskListId, UUID taskId, Task task) {
        if(task.getId()==null){
            throw new IllegalArgumentException("Task must have an Id");
        }
        if(!task.getId().equals(taskId)){
            throw new IllegalArgumentException("Task IDs do not match");
        }
        if(task.getPriority()==null){
            throw new IllegalArgumentException("Task must have a valid priority");
        }
        if(task.getStatus()==null){
            throw new IllegalArgumentException("Task must have a valid status");
        }
        Task existingTask = taskRepository.findByTaskListIdAndId(taskListId, taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found"));
        existingTask.setTitle(task.getTitle());
        existingTask.setDescription(task.getDescription());
        existingTask.setDueDate(task.getDueDate());
        existingTask.setDueDate(task.getDueDate());
        existingTask.setStatus(task.getStatus());
        existingTask.setUpdated(LocalDateTime.now());

        return taskRepository.save(existingTask);
    }

    @Override
    @Transactional
    public void deleteTask(UUID taskListId, UUID taskId) {
        taskRepository.deleteByTaskListIdAndId(taskListId, taskId);
    }
}
