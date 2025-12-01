package com.dev.tasks.services.impl;

import com.dev.tasks.domain.dto.TaskDto;
import com.dev.tasks.domain.entities.Task;
import com.dev.tasks.domain.entities.TaskList;
import com.dev.tasks.domain.entities.TaskPriority;
import com.dev.tasks.domain.entities.TaskStatus;
import com.dev.tasks.repositories.TaskListRepository;
import com.dev.tasks.repositories.TaskRepository;
import com.dev.tasks.services.TaskListService;
import com.dev.tasks.services.TaskService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskServiceImpl implements TaskService {
    private TaskListService taskListService;
    private final TaskRepository taskRepository;
    private final TaskListRepository taskListRepository;
    public TaskServiceImpl(TaskRepository taskRepository,TaskListService taskListService,TaskListRepository taskListRepository) {
        this.taskListService = taskListService;
        this.taskRepository = taskRepository;
        this.taskListRepository = taskListRepository;
    }

    @Override
    public List<Task> listTasks(UUID id) {
        return taskRepository.findByTaskListId(id);
    }

    @Override
    public Task createTask(UUID id, Task task) {
        if(null!=task.getId()){
            throw new IllegalArgumentException("Task already has an id");
        }
        if(task.getTitle()==null || task.getTitle().isEmpty()){
            throw new IllegalArgumentException("Task title is empty");
        }
        TaskPriority taskPriority= Optional.ofNullable(task.getPriority()).orElse(TaskPriority.MEDIUM);
        TaskStatus taskStatus=TaskStatus.OPEN;
        TaskList taskList=taskListRepository.findById(id).orElseThrow(()->new IllegalArgumentException("Task not found"));
        LocalDateTime now=LocalDateTime.now();
        Task TaskToSave=new Task(
                null,
                task.getTitle(),
                task.getDescription(),
                task.getDueDate(),
                taskStatus,
                taskPriority,
                taskList,
                now,
                now
        );
        return taskRepository.save(TaskToSave);
    }

    @Override
    public Optional<Task> getTask(UUID id, UUID taskId) {
        if(null==taskId){
            throw new IllegalArgumentException("Task id is null");
        }
        if(null==id){
            throw new IllegalArgumentException("Task id is null");
        }
        return taskRepository.findByTaskListIdAndId(id,taskId);
    }

    @Override
    public Task updateTask(UUID id, UUID taskId, Task task) {

        if(null==task.getId())
        {
            throw new IllegalArgumentException("Task id is null");
        }
        if(!Objects.equals(task.getId(),taskId)){
            throw new IllegalArgumentException("Task id does not match");
        }
        if(null==task.getPriority())
        {
            throw new IllegalArgumentException("Task priority is null");
        }
        if(null==task.getStatus())
        {
            throw new IllegalArgumentException("Task status is null");
        }
        Task Existingtask=taskRepository.findByTaskListIdAndId(id,taskId).orElseThrow(()->new IllegalArgumentException("Task not found"));
        Existingtask.setTitle(task.getTitle());
        Existingtask.setDescription(task.getDescription());
        Existingtask.setDueDate(task.getDueDate());
        Existingtask.setPriority(task.getPriority());
        Existingtask.setStatus(task.getStatus());
        Existingtask.setUpdated(LocalDateTime.now());
        return taskRepository.save(Existingtask);
    }
    @Transactional
    @Override
    public void deleteTask(UUID id, UUID taskId) {
        taskRepository.deleteByTaskListIdAndId(id,taskId);
    }


}
