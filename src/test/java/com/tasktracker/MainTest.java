package com.tasktracker;

import com.tasktracker.service.TaskService;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MainTest {

    @Test
    void printsMessageWhenNoCommandProvided() {
        RecordingTaskService service = new RecordingTaskService();
        String output = runAndCaptureOutput(new String[]{}, service);

        assertTrue(output.contains("No command provided."));
    }

    @Test
    void printsMessageForUnknownCommand() {
        RecordingTaskService service = new RecordingTaskService();
        String output = runAndCaptureOutput(new String[]{"noop"}, service);

        assertTrue(output.contains("Unknown command."));
    }

    @Test
    void rejectsBlankDescriptionOnAdd() {
        RecordingTaskService service = new RecordingTaskService();
        String output = runAndCaptureOutput(new String[]{"add", "   "}, service);

        assertTrue(output.contains("Description required."));
        assertFalse(service.addCalled);
    }

    @Test
    void rejectsBlankDescriptionOnUpdate() {
        RecordingTaskService service = new RecordingTaskService();
        String output = runAndCaptureOutput(new String[]{"update", "1", "   "}, service);

        assertTrue(output.contains("Description required."));
        assertFalse(service.updateCalled);
    }

    @Test
    void rejectsTooManyArgumentsOnAdd() {
        RecordingTaskService service = new RecordingTaskService();
        String output = runAndCaptureOutput(new String[]{"add", "a", "b"}, service);

        assertTrue(output.contains("Too many arguments for add."));
        assertFalse(service.addCalled);
    }

    @Test
    void rejectsTooManyArgumentsOnUpdate() {
        RecordingTaskService service = new RecordingTaskService();
        String output = runAndCaptureOutput(new String[]{"update", "1", "a", "b"}, service);

        assertTrue(output.contains("Too many arguments for update."));
        assertFalse(service.updateCalled);
    }

    @Test
    void rejectsTooManyArgumentsOnDelete() {
        RecordingTaskService service = new RecordingTaskService();
        String output = runAndCaptureOutput(new String[]{"delete", "1", "extra"}, service);

        assertTrue(output.contains("Too many arguments for delete."));
        assertFalse(service.deleteCalled);
    }

    @Test
    void rejectsTooManyArgumentsOnMarkDone() {
        RecordingTaskService service = new RecordingTaskService();
        String output = runAndCaptureOutput(new String[]{"mark-done", "1", "extra"}, service);

        assertTrue(output.contains("Too many arguments for mark-done."));
        assertFalse(service.markDoneCalled);
    }

    @Test
    void requiresTaskIdForDelete() {
        RecordingTaskService service = new RecordingTaskService();
        String output = runAndCaptureOutput(new String[]{"delete"}, service);

        assertTrue(output.contains("Task id required."));
        assertFalse(service.deleteCalled);
    }

    @Test
    void rejectsNonPositiveTaskId() {
        RecordingTaskService service = new RecordingTaskService();
        String output = runAndCaptureOutput(new String[]{"mark-done", "0"}, service);

        assertTrue(output.contains("Invalid task id."));
        assertFalse(service.markDoneCalled);
    }

    @Test
    void rejectsInvalidStatusForList() {
        RecordingTaskService service = new RecordingTaskService();
        String output = runAndCaptureOutput(new String[]{"list", "doing"}, service);

        assertTrue(output.contains("Invalid status. Use: todo, in-progress, done."));
        assertFalse(service.listByStatusCalled);
    }

    @Test
    void rejectsTooManyArgumentsForList() {
        RecordingTaskService service = new RecordingTaskService();
        String output = runAndCaptureOutput(new String[]{"list", "todo", "extra"}, service);

        assertTrue(output.contains("Too many arguments for list."));
        assertFalse(service.listAllCalled);
        assertFalse(service.listByStatusCalled);
    }

    @Test
    void routesListWithoutStatusToListAll() {
        RecordingTaskService service = new RecordingTaskService();
        runAndCaptureOutput(new String[]{"list"}, service);

        assertTrue(service.listAllCalled);
        assertFalse(service.listByStatusCalled);
    }

    @Test
    void routesListWithStatusToListByStatus() {
        RecordingTaskService service = new RecordingTaskService();
        runAndCaptureOutput(new String[]{"list", "done"}, service);

        assertFalse(service.listAllCalled);
        assertTrue(service.listByStatusCalled);
        assertEquals("done", service.listStatus);
    }

    @Test
    void rejectsNonNumericTaskId() {
        RecordingTaskService service = new RecordingTaskService();
        String output = runAndCaptureOutput(new String[]{"delete", "abc"}, service);

        assertTrue(output.contains("Invalid task id."));
        assertFalse(service.deleteCalled);
    }

    @Test
    void routesMarkInProgressWhenValid() {
        RecordingTaskService service = new RecordingTaskService();
        runAndCaptureOutput(new String[]{"mark-in-progress", "1"}, service);

        assertTrue(service.markInProgressCalled);
    }

    @Test
    void routesDeleteWhenValid() {
        RecordingTaskService service = new RecordingTaskService();
        runAndCaptureOutput(new String[]{"delete", "1"}, service);

        assertTrue(service.deleteCalled);
    }

    @Test
    void routesUpdateWhenValid() {
        RecordingTaskService service = new RecordingTaskService();
        runAndCaptureOutput(new String[]{"update", "1", "new"}, service);

        assertTrue(service.updateCalled);
    }

    @Test
    void routesAddWhenValid() {
        RecordingTaskService service = new RecordingTaskService();
        runAndCaptureOutput(new String[]{"add", "new"}, service);

        assertTrue(service.addCalled);
    }

    @Test
    void routesMarkDoneWhenValid() {
        RecordingTaskService service = new RecordingTaskService();
        runAndCaptureOutput(new String[]{"mark-done", "1"}, service);

        assertTrue(service.markDoneCalled);
    }

    private static String runAndCaptureOutput(String[] args, TaskService service) {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        try {
            Main.run(args, service);
        } finally {
            System.setOut(originalOut);
        }
        return outputStream.toString();
    }

    private static class RecordingTaskService extends TaskService {
        boolean addCalled;
        boolean updateCalled;
        boolean deleteCalled;
        boolean markDoneCalled;
        boolean markInProgressCalled;
        boolean listAllCalled;
        boolean listByStatusCalled;
        String listStatus;

        @Override
        public void addTask(String description) {
            addCalled = true;
        }

        @Override
        public boolean updateTask(int id, String description) {
            updateCalled = true;
            return true;
        }

        @Override
        public boolean deleteTask(int id) {
            deleteCalled = true;
            return true;
        }

        @Override
        public boolean markTaskDone(int id) {
            markDoneCalled = true;
            return true;
        }

        @Override
        public boolean markTaskInProgress(int id) {
            markInProgressCalled = true;
            return true;
        }

        @Override
        public void listTasks() {
            listAllCalled = true;
        }

        @Override
        public void listTasks(String status) {
            listByStatusCalled = true;
            listStatus = status;
        }
    }
}
