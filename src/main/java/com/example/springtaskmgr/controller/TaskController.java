package com.example.springtaskmgr.controller;

import com.example.springtaskmgr.dtos.ErrorResponse;
import com.example.springtaskmgr.entities.Task;
import com.example.springtaskmgr.services.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
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
    ResponseEntity<List<Task> > getTaskList(){
        return ResponseEntity.ok(taskService.getTasks());
    }

    @PostMapping("/tasks")
    ResponseEntity<Task> createTask(@RequestBody Task task){
        var newTask = taskService.createTask(task.getTitle(), task.getDescription(), task.getDueDate());
        return ResponseEntity.created(URI.create("/tasks/"+newTask.getId())).body(newTask);
    }

    @GetMapping("/tasks/{id}")
    ResponseEntity<Task> getTask(@PathVariable("id") Integer id){
       return ResponseEntity.ok(taskService.getTaskById(id));
    }

    @DeleteMapping("/tasks/{id}")
    ResponseEntity<Task> deleteTask(@PathVariable("id") Integer id){
        return ResponseEntity.accepted().body(taskService.deleteTask(id));
    }

    @PatchMapping("/tasks/{id}")
    ResponseEntity<Task> updateTask(@PathVariable("id") Integer id,  @RequestBody Task task){
       var updatedTask = taskService.updateTask
               (id, task.getTitle(), task.getDescription(), task.getDueDate().toString());
       return ResponseEntity.accepted().body(updatedTask);
    }

    @ExceptionHandler(TaskService.TaskNotFoundException.class)
    ResponseEntity<ErrorResponse> handleError(TaskService.TaskNotFoundException e){
        return new ResponseEntity<>(
                new ErrorResponse(e.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }
}
