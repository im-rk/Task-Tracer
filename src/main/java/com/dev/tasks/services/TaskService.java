package com.dev.tasks.services;

import com.dev.tasks.domain.entities.Task;

import java.util.List;
import java.util.UUID;
import java.util.Optional;
public interface TaskService {
    List<Task> listTasks(UUID id);
    Task createTask(UUID id,Task task);
    Optional<Task> getTask(UUID id,UUID taskId);
    Task updateTask(UUID id,UUID taskId,Task task);
}
