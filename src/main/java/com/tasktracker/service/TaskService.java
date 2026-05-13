package com.tasktracker.service;

import com.tasktracker.model.Task;
import com.tasktracker.storage.TaskStorage;

import java.time.LocalDateTime;
import java.util.List;

public class TaskService {

    private final TaskStorage storage = new TaskStorage();

    public void addTask(String description) {

        List<Task> tasks = storage.loadTasks();

        int newId = tasks.stream()
                .mapToInt(Task::getId)
                .max()
                .orElse(0) + 1;

        String now = LocalDateTime.now().toString();

        Task task = new Task(
                newId,
                description,
                "todo",
                now,
                now
        );

        tasks.add(task);

        storage.saveTasks(tasks);

        System.out.println("Task added successfully.");
    }

    public void listTasks() {

        List<Task> tasks = storage.loadTasks();

        for (Task task : tasks) {
            System.out.println(task);
        }
    }
}