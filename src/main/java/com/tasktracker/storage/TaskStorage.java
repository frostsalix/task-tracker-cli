package com.tasktracker.storage;

import com.tasktracker.model.Task;
import com.tasktracker.util.JsonUtil;

import java.util.List;

public class TaskStorage {

    private static final String DEFAULT_FILE_NAME = "tasks.json";
    private final String fileName;

    public TaskStorage() {
        this(DEFAULT_FILE_NAME);
    }

    public TaskStorage(String fileName) {
        this.fileName = fileName;
    }

    public List<Task> loadTasks() {
        return JsonUtil.readTasks(fileName);
    }

    public void saveTasks(List<Task> tasks) {
        JsonUtil.writeTasks(fileName, tasks);
    }
}