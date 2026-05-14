package com.tasktracker.service;

import com.tasktracker.model.Task;
import com.tasktracker.storage.TaskStorage;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class TaskServiceTest {

    @Test
    void updatesTaskDescriptionAndUpdatedAt() throws Exception {
        Path tempFile = Files.createTempFile("tasks", ".json");
        TaskStorage storage = new TaskStorage(tempFile.toString());
        TaskService service = new TaskService(storage);

        storage.saveTasks(List.of(new Task(1, "old", "todo", "2026-01-01T10:00:00", "2026-01-01T10:00:00")));

        service.updateTask(1, "new");

        List<Task> tasks = storage.loadTasks();
        assertEquals(1, tasks.size());
        assertEquals("new", tasks.get(0).getDescription());
        assertNotEquals("2026-01-01T10:00:00", tasks.get(0).getUpdatedAt());
    }

    @Test
    void deletesTaskById() throws Exception {
        Path tempFile = Files.createTempFile("tasks", ".json");
        TaskStorage storage = new TaskStorage(tempFile.toString());
        TaskService service = new TaskService(storage);

        storage.saveTasks(List.of(
                new Task(1, "a", "todo", "2026-01-01T10:00:00", "2026-01-01T10:00:00"),
                new Task(2, "b", "todo", "2026-01-01T10:00:00", "2026-01-01T10:00:00")
        ));

        service.deleteTask(1);

        List<Task> tasks = storage.loadTasks();
        assertEquals(1, tasks.size());
        assertEquals(2, tasks.get(0).getId());
    }

    @Test
    void marksTaskAsInProgress() throws Exception {
        Path tempFile = Files.createTempFile("tasks", ".json");
        TaskStorage storage = new TaskStorage(tempFile.toString());
        TaskService service = new TaskService(storage);

        storage.saveTasks(List.of(new Task(1, "a", "todo", "2026-01-01T10:00:00", "2026-01-01T10:00:00")));

        service.markTaskInProgress(1);

        List<Task> tasks = storage.loadTasks();
        assertEquals("in-progress", tasks.get(0).getStatus());
    }

    @Test
    void marksTaskAsDone() throws Exception {
        Path tempFile = Files.createTempFile("tasks", ".json");
        TaskStorage storage = new TaskStorage(tempFile.toString());
        TaskService service = new TaskService(storage);

        storage.saveTasks(List.of(new Task(1, "a", "todo", "2026-01-01T10:00:00", "2026-01-01T10:00:00")));

        service.markTaskDone(1);

        List<Task> tasks = storage.loadTasks();
        assertEquals("done", tasks.get(0).getStatus());
    }

    @Test
    void returnsFalseWhenTaskNotFoundOnUpdate() throws Exception {
        Path tempFile = Files.createTempFile("tasks", ".json");
        TaskStorage storage = new TaskStorage(tempFile.toString());
        TaskService service = new TaskService(storage);

        storage.saveTasks(List.of(new Task(1, "a", "todo", "2026-01-01T10:00:00", "2026-01-01T10:00:00")));

        boolean updated = service.updateTask(99, "new");

        assertFalse(updated);
    }

    @Test
    void filtersTasksByDoneStatus() throws Exception {
        Path tempFile = Files.createTempFile("tasks", ".json");
        TaskStorage storage = new TaskStorage(tempFile.toString());
        TaskService service = new TaskService(storage);

        storage.saveTasks(List.of(
                new Task(1, "a", "todo", "2026-01-01T10:00:00", "2026-01-01T10:00:00"),
                new Task(2, "b", "done", "2026-01-01T10:00:00", "2026-01-01T10:00:00"),
                new Task(3, "c", "in-progress", "2026-01-01T10:00:00", "2026-01-01T10:00:00")
        ));

        List<Task> filtered = service.getTasksByStatus("done");

        assertEquals(1, filtered.size());
        assertEquals(2, filtered.get(0).getId());
    }
}
