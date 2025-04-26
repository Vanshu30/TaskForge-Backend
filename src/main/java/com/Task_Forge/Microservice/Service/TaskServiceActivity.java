 package com.Task_Forge.Microservice.Service;

import com.Task_Forge.Microservice.Entity.TaskActivity;
import com.Task_Forge.Microservice.Repository.TaskActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskServiceActivity {

    @Autowired
    TaskActivityRepository taskActivityRepository;

    public long getUpdatesInLast7Days(){
        LocalDateTime sevenDaysAgo = LocalDateTime.now().minusDays(7);
        return taskActivityRepository.countUpdatesInLast7Days(sevenDaysAgo);
    }

    public long getDueSoonTasks(){
        LocalDateTime sevenDaysAgo = LocalDateTime.now().minusDays(7);
        LocalDateTime now = LocalDateTime.now();
        return taskActivityRepository.countDueSoonTasks(sevenDaysAgo, now);
    }
}

