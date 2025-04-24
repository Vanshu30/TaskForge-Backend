package com.Task_Forge.Microservice.Entity;

import com.Task_Forge.Microservice.ENUM.ActivityType;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
public class TaskActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    private Task task; // Links to the Task entity


    @Column(nullable = false)
    private ActivityType activityType;

    @Column(columnDefinition = "TEXT")
    private String details; // Stores comments or status change details

    @Column(nullable = false)
    private LocalDateTime updatedAt; // Timestamp when updated occurred

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User updatedBy; // Tracks user who the update
}