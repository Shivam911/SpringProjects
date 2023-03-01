package com.example.springtaskmgr.services;

import com.example.springtaskmgr.entities.Task;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class TaskService {

    private final List<Task> taskList;
    AtomicInteger taskId = new AtomicInteger(0);

    public static class TaskNotFoundException extends IllegalStateException{
        public TaskNotFoundException(Integer id){
            super("Task with id "+ id + " not found");
        }
    }

    public TaskService() {
        taskList = new ArrayList<>();
        taskList.add(new Task(taskId.incrementAndGet(),"Task 1", "Description 1", "2021-01-01"));
        taskList.add(new Task(taskId.incrementAndGet(),"Task 2", "Description 2", "2021-01-01"));
        taskList.add(new Task(taskId.incrementAndGet(),"Task 3", "Description 3", "2021-01-01"));
    }

    public List<Task> getTasks(){
        return taskList;
    }

    public Task createTask(String title, String description, String dueDate){
        var newTask = new Task(taskId.incrementAndGet(), title, description, dueDate);
        taskList.add(newTask);
        return newTask;
    }

    public Task getTaskById(Integer id){
        for(Task task : taskList){
            if(task.getId().equals(id)){
                return task;
            }
        }
        throw new TaskNotFoundException(id);
    }

    public Task updateTask(Integer id, String title, String description, String dueDate){
         var task = getTaskById(id);
         if(title != null) task.setTitle(title);
         if(description != null) task.setDescription(description);
         if(dueDate != null) task.setDueDate(dueDate);
         return task;
    }

    public Task deleteTask(Integer id){
        var task = getTaskById(id);
        if(task == null){
            throw new TaskNotFoundException(id);
        }
        taskList.remove(task);
        return task;
    }


}
