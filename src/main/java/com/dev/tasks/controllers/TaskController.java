package com.dev.tasks.controllers;

import com.dev.tasks.domain.dto.TaskDto;
import com.dev.tasks.domain.entities.Task;
import com.dev.tasks.mappers.TaskMapper;
import com.dev.tasks.services.TaskService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path="/task-lists/{task_list_id}/tasks")
public class TaskController {

    private final TaskService taskService;
    private final TaskMapper taskMapper;
    public TaskController(TaskMapper taskMapper, TaskService taskService) {
        this.taskMapper = taskMapper;
        this.taskService = taskService;
    }

    @GetMapping
    public List<TaskDto> listTasks(@PathVariable("task_list_id") UUID task_list_id) {
        return taskService.listTasks(task_list_id).stream().map(taskMapper::toDto).toList();
    }

    @PostMapping
    public TaskDto createTask(@PathVariable("task_list_id") UUID task_list_id,@RequestBody TaskDto taskDto)
    {
        Task task=taskService.createTask(task_list_id,taskMapper.fromDto(taskDto));
        return taskMapper.toDto(task);
    }

    @GetMapping(path="/{task_id}")
    public Optional<TaskDto> getTask(@PathVariable("task_list_id") UUID task_list_id,@PathVariable("task_id") UUID task_id) {
        return taskService.getTask(task_list_id,task_id).map(taskMapper::toDto);
    }

    @GetMapping(path="/{task_id}")
    public TaskDto updateTask(@PathVariable("task_list_id") UUID task_list_id,@PathVariable("task_id") UUID task_id,@RequestBody TaskDto taskDto)
    {
        Task task=taskService.updateTask(task_list_id,task_id,taskMapper.fromDto(taskDto));
        return taskMapper.toDto(task);
    }

}
