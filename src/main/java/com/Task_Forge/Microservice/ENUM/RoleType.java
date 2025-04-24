package com.Task_Forge.Microservice.ENUM;

public enum RoleType {
    USER,
    ADMIN,           // Full access to everything
    PROJECT_MANAGER, // Manages projects & tasks
    DEVELOPER,       // Works on assigned tasks
    TESTER,          // Tests the application
    STAKEHOLDER      // Can view project progress
}
