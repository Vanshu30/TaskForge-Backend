package com.Task_Forge.Microservice.Service;

import com.Task_Forge.Microservice.DTO.TaskDTO;
import com.Task_Forge.Microservice.ENUM.TaskStatus;
import com.Task_Forge.Microservice.Entity.Project;
import com.Task_Forge.Microservice.Entity.Task;
import com.Task_Forge.Microservice.Entity.User;
import com.Task_Forge.Microservice.Exception.ResourceNotFoundException;
import com.Task_Forge.Microservice.Repository.ProjectRepository;
import com.Task_Forge.Microservice.Repository.TaskRepository;
import com.Task_Forge.Microservice.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    @Transactional
    public Task createTask(TaskDTO taskDTO, UUID projectId, UUID assignedToId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));

        User assignedUser = userRepository.findById(assignedToId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Task task = new Task();
        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setStatus(TaskStatus.TO_DO);
        task.setProject(project);
        task.setAssignedTo(assignedUser);

        return taskRepository.save(task);
    }

    @Transactional(readOnly = true)
    public long getTasksCountLast7Days(UUID userId) {
        LocalDateTime lastWeek = LocalDateTime.now().minusDays(7);
        return taskRepository.countTasksLast7Days(userId, lastWeek);
    }

    @Transactional(readOnly = true)
    public int getCompletedTasksCount(UUID employeeId) {
        LocalDateTime sevenDaysAgo = LocalDateTime.now().minusDays(7);
        return taskRepository.countCompletedTasks(employeeId, sevenDaysAgo);
    }
    @Transactional(readOnly = true)
    public Map<String, Integer> getMonthlyCompletedTasksForLoggedInUser(UUID userId) {
        // Get logged-in user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserEmail = authentication.getName();
        User user = userRepository.findByEmail(currentUserEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        List<Task> completedTasks = taskRepository.findByAssignedToAndStatus(user, TaskStatus.COMPLETED);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");

        // Group by Year-Month and count
        return completedTasks.stream()
                .filter((Task task) -> task.getUpdatedAt() != null)
                .collect(Collectors.groupingBy(
                        task -> task.getUpdatedAt().format(formatter),
                        TreeMap::new,
                        Collectors.collectingAndThen(Collectors.counting(), Long::intValue)
                ));



    }


    @Transactional(readOnly = true)
    public Task getTaskById(UUID taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));
    }

    @Transactional
    public Task updateTask(UUID taskId, TaskDTO taskDTO) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));

        // Get the currently logged-in user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserEmail = authentication.getName();

        // Check if the user trying to update the task is the assigned user
        if (task.getAssignedTo() == null || !task.getAssignedTo().getEmail().equals(currentUserEmail)) {
            throw new AccessDeniedException("You do not have permission to update this task");
        }

        // Updating task fields
        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setStatus(TaskStatus.valueOf(taskDTO.getStatus().toUpperCase()));

        return taskRepository.save(task);
    }

    @Transactional(readOnly = true)
    public long countUpdatesInLast7Days() {
        LocalDateTime sevenDaysAgo = LocalDateTime.now().minusDays(7);
        long count = taskRepository.countUpdatesInLast7Days(sevenDaysAgo);
        log.info("Found {} updates in the last 7 days", count);
        return count;
    }

    @Transactional(readOnly = true)
    public long countDueSoonTasks() {
        LocalDateTime sevenDaysAgo = LocalDateTime.now().minusDays(7);
        LocalDateTime now = LocalDateTime.now();
        long count = taskRepository.countDueSoonTasks(sevenDaysAgo, now);
        log.info("Found {} tasks due in the last 7 days", count);
        return count;
    }
}