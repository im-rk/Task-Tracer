package com.dev.tasks.domain.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Tasks")
public class Task {

    @Id
    @GeneratedValue(strategy=GenerationType.UUID)
    @Column(name="id",updatable=false,nullable=false)
    private UUID id;

    @Column(name="title",nullable=false)
    private String title;

    @Column(name="description",nullable=true)
    private String description;
    
    @Column(name="due_date")
    private LocalDateTime dueDate;

    @Column(name="status",nullable=false)
    private TaskStatus status;

}
