package com.Task_Forge.Microservice.ENUM;

public enum TaskStatus {
    TO_DO,       // Task is planned but not started
    IN_PROGRESS, // Task is currently being worked on
    REVIEW,      // Task is under review/testing
    COMPLETED,   // Task is finished
    BLOCKED      // Task is blocked due to an issue
}
