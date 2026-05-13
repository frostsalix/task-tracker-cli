package com.tasktracker.storage;

import com.tasktracker.model.Task;
import com.tasktracker.util.JsonUtil;

import java.util.List;

public class TaskStorage {

    private static final String FILE_NAME = "tasks.json";

    public List<Task> loadTasks() {
        return JsonUtil.readTasks(FILE_NAME);
    }

    public void saveTasks(List<Task> tasks) {
        JsonUtil.writeTasks(FILE_NAME, tasks);
    }
}