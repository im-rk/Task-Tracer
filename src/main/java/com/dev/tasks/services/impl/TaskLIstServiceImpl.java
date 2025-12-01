package com.dev.tasks.services.impl;

import com.dev.tasks.domain.entities.TaskList;
import com.dev.tasks.repositories.TaskListRepository;
import com.dev.tasks.services.TaskListService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskLIstServiceImpl implements TaskListService {

    private final transient TaskListRepository taskListRepository;
    public TaskLIstServiceImpl(TaskListRepository taskListRepository) {this.taskListRepository = taskListRepository;}

    @Override
    public List<TaskList> listTaskLists() {
        return taskListRepository.findAll();
    }

    @Override
    public TaskList createTaskList(TaskList taskList) {
        if(taskList.getId()!=null)
        {
            throw new IllegalArgumentException("Task list already has an id");
        }
        if(taskList.getTitle()==null || taskList.getTitle().isBlank())
        {
            throw new IllegalArgumentException("Task list title is required");
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
    public TaskList updateTaskList(UUID id, TaskList taskLIst) {
        if(null==taskLIst.getId())
        {
            throw new IllegalArgumentException("Task list id is required");
        }
        if(!Objects.equals(id,taskLIst.getId())){
            throw new IllegalArgumentException("Task list id does not match");
        }

        TaskList existingTaskList=taskListRepository.findById(id).orElseThrow(()->
                new IllegalArgumentException("Task list not found"));

        existingTaskList.setTitle(taskLIst.getTitle());
        existingTaskList.setDescription(taskLIst.getDescription());
        existingTaskList.setUpdated(LocalDateTime.now());
        return taskListRepository.save(existingTaskList);
    }

    @Override
    public void deleteTaskList(UUID id) {
        if(null==id)
        {
            throw new IllegalArgumentException("Task list id is required");
        }
        taskListRepository.deleteById(id);
    }


}
