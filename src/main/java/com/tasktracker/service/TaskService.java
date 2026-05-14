package com.tasktracker.service;

import com.tasktracker.model.Task;
import com.tasktracker.storage.TaskStorage;

import java.time.LocalDateTime;
import java.util.List;

public class TaskService {

    private final TaskStorage storage;

    public TaskService() {
        this(new TaskStorage());
    }

    public TaskService(TaskStorage storage) {
        this.storage = storage;
    }

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

    public boolean updateTask(int id, String description) {
        List<Task> tasks = storage.loadTasks();

        for (Task task : tasks) {
            if (task.getId() == id) {
                task.setDescription(description);
                task.setUpdatedAt(LocalDateTime.now().toString());
                storage.saveTasks(tasks);
                System.out.println("Task updated successfully.");
                return true;
            }
        }

        System.out.println("Task not found.");
        return false;
    }

    public boolean deleteTask(int id) {
        List<Task> tasks = storage.loadTasks();
        boolean removed = tasks.removeIf(task -> task.getId() == id);

        if (removed) {
            storage.saveTasks(tasks);
            System.out.println("Task deleted successfully.");
            return true;
        }

        System.out.println("Task not found.");
        return false;
    }

    public boolean markTaskInProgress(int id) {
        return updateTaskStatus(id, "in-progress", "Task marked as in-progress.");
    }

    public boolean markTaskDone(int id) {
        return updateTaskStatus(id, "done", "Task marked as done.");
    }

    private boolean updateTaskStatus(int id, String status, String successMessage) {
        List<Task> tasks = storage.loadTasks();

        for (Task task : tasks) {
            if (task.getId() == id) {
                task.setStatus(status);
                task.setUpdatedAt(LocalDateTime.now().toString());
                storage.saveTasks(tasks);
                System.out.println(successMessage);
                return true;
            }
        }

        System.out.println("Task not found.");
        return false;
    }
}