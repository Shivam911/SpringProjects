package com.example.springtaskmgr.controller;

import com.example.springtaskmgr.entities.Task;
import com.example.springtaskmgr.services.TaskService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class TaskController {

    private TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/tasks")
    List<Task> getTaskList(){
        return taskService.getTasks();
    }

    @PostMapping("/tasks")
    Task createTask(@RequestBody Task task){
        var newTask = taskService.createTask(task.getTitle(), task.getDescription(), task.getDueDate().toString());
        return newTask;
    }

    @GetMapping("/tasks/{id}")
    Task getTask(@PathVariable("id") Integer id){
       return taskService.getTaskById(id);
    }

    @DeleteMapping("/tasks/{id}")
    Task deleteTask(@PathVariable("id") Integer id){
        return taskService.deleteTask(id);
    }

    @PatchMapping("/tasks/{id}")
    Task updateTask(@PathVariable("id") Integer id,  @RequestBody Task task){
       var updatedTask = taskService.updateTask
               (id, task.getTitle(), task.getDescription(), task.getDueDate().toString());
       return updatedTask;
    }
}
