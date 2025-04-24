package com.Task_Forge.Microservice.Repository;

import com.Task_Forge.Microservice.ENUM.TaskStatus;
import com.Task_Forge.Microservice.Entity.Task;
import com.Task_Forge.Microservice.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {

    @Query("SELECT COUNT(t) FROM Task t WHERE t.assignedTo.id = :employeeId AND t.status = :status AND t.completedAt >= :sevenDaysAgo")
    int countCompletedTasks(@Param("employeeId") UUID employeeId, @Param("sevenDaysAgo") LocalDateTime sevenDaysAgo);

    @Query("SELECT COUNT(t) FROM Task t WHERE t.assignedTo.id = :userId AND t.createdAt >= :lastWeek")
    long countTasksLast7Days(@Param("userId") UUID userId, @Param("lastWeek") LocalDateTime lastWeek);

    @Query("SELECT COUNT(t) FROM Task t WHERE t.lastUpdatedAt >= :sevenDaysAgo")
    long countUpdatesInLast7Days(@Param("sevenDaysAgo") LocalDateTime sevenDaysAgo);  // Fixed field name

    List<Task> findByAssignedToAndStatus(User user, TaskStatus status);

    @Query("SELECT COUNT(t) FROM TaskActivity t WHERE t.dueDate BETWEEN :from AND :to")
    long countDueSoonTasks(@Param("from") LocalDateTime from, @Param("to") LocalDateTime to);

}