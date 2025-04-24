package com.Task_Forge.Microservice.Controller;

import com.Task_Forge.Microservice.Entity.TaskActivity;
import com.Task_Forge.Microservice.Service.TaskServiceActivity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/task-activities")
public class TaskActivityController {

    @Autowired
    TaskServiceActivity taskServiceActivity;

    @GetMapping("/stats")
    public Map<String, Long> getUpdatesInLast7Days(){
        long updatesInLast7Days = taskServiceActivity.getUpdatesInLast7Days();
        long dueSoonTasks = taskServiceActivity.getDueSoonTasks();

        return Map.of("updatesLast7Days", updatesInLast7Days,
                "dueSoonTasks", dueSoonTasks);
    }
}