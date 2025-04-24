package com.Task_Forge.Microservice.Repository;

import com.Task_Forge.Microservice.Entity.TaskActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface TaskActivityRepository extends JpaRepository<TaskActivity, Long> {

    @Query("SELECT COUNT(t) FROM TaskActivity t WHERE t.updatedAt >= :sevenDaysAgo")
    long countUpdatesInLast7Days(@Param("sevenDaysAgo") LocalDateTime sevenDaysAgo);

    @Query("SELECT COUNT(t) FROM TaskActivity t WHERE t.dueDate >= :sevenDaysAgo AND t.dueDate <= :now")
    long countDueSoonTasks(@Param("sevenDaysAgo") LocalDateTime sevenDaysAgo, @Param("now") LocalDateTime now);
}
