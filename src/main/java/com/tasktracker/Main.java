package com.tasktracker;

import com.tasktracker.service.TaskService;

public class Main {

    public static void main(String[] args) {

        TaskService service = new TaskService();

        if (args.length == 0) {
            System.out.println("No command provided.");
            return;
        }

        String command = args[0];

        switch (command) {

            case "add":

                if (args.length < 2) {
                    System.out.println("Description required.");
                    return;
                }

                service.addTask(args[1]);
                break;

            case "list":
                service.listTasks();
                break;

            case "update":
                if (args.length < 3) {
                    System.out.println("Task id and description required.");
                    return;
                }

                Integer updateId = parseTaskId(args[1]);
                if (updateId == null) {
                    return;
                }

                service.updateTask(updateId, args[2]);
                break;

            case "delete":
                if (args.length < 2) {
                    System.out.println("Task id required.");
                    return;
                }

                Integer deleteId = parseTaskId(args[1]);
                if (deleteId == null) {
                    return;
                }

                service.deleteTask(deleteId);
                break;

            case "mark-in-progress":
                if (args.length < 2) {
                    System.out.println("Task id required.");
                    return;
                }

                Integer inProgressId = parseTaskId(args[1]);
                if (inProgressId == null) {
                    return;
                }

                service.markTaskInProgress(inProgressId);
                break;

            case "mark-done":
                if (args.length < 2) {
                    System.out.println("Task id required.");
                    return;
                }

                Integer doneId = parseTaskId(args[1]);
                if (doneId == null) {
                    return;
                }

                service.markTaskDone(doneId);
                break;

            default:
                System.out.println("Unknown command.");
        }
    }

    private static Integer parseTaskId(String rawId) {
        try {
            return Integer.parseInt(rawId);
        } catch (NumberFormatException e) {
            System.out.println("Invalid task id.");
            return null;
        }
    }
}