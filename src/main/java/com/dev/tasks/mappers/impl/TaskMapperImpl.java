package com.dev.tasks.mappers.impl;

import com.dev.tasks.domain.dto.TaskDto;
import com.dev.tasks.domain.entities.Task;
import com.dev.tasks.mappers.TaskMapper;
import jakarta.persistence.Column;
import org.springframework.stereotype.Component;

@Component
public class TaskMapperImpl  implements TaskMapper {
    @Override
    public Task fromDto(TaskDto taskdto) {
        return new Task(
                taskdto.id(),
                taskdto.title(),
                taskdto.description(),
                taskdto.dueDate(),
                taskdto.status(),
                taskdto.priority(),
                null,
                null,
                null
        );
    }

    @Override
    public TaskDto toDto(Task task) {
        return new  TaskDto(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getDueDate(),
                task.getPriority(),
                task.getStatus()
        );
    }
}
