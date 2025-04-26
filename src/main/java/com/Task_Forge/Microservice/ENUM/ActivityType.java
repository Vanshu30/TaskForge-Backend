package com.Task_Forge.Microservice.ENUM;

public enum ActivityType {
    CREATED,          // Task was created
    UPDATED,          // Task details were modified
    STATUS_CHANGED,   // Task status changed (e.g., from OPEN to IN_PROGRESS)
    COMMENT_ADDED,    // A comment was added to the task
    PRIORITY_CHANGED  // Task priority was updated
}