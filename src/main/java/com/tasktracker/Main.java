package com.tasktracker;

import com.tasktracker.service.TaskService;

public class Main {

    public static void main(String[] args) {
        run(args, new TaskService());
    }

    static void run(String[] args, TaskService service) {
        if (args.length == 0) {
            System.out.println("No command provided.");
            return;
        }

        String command = args[0];

        switch (command) {

            case "add":

                if (args.length < 2 || isBlank(args[1])) {
                    System.out.println("Description required.");
                    return;
                }

                if (args.length > 2) {
                    System.out.println("Too many arguments for add.");
                    return;
                }

                service.addTask(args[1]);
                break;

            case "list":
                if (args.length == 1) {
                    service.listTasks();
                    break;
                }

                if (args.length > 2) {
                    System.out.println("Too many arguments for list.");
                    return;
                }

                String status = args[1];
                if (!isValidStatus(status)) {
                    System.out.println("Invalid status. Use: todo, in-progress, done.");
                    return;
                }

                service.listTasks(status);
                break;

            case "update":
                if (args.length < 3) {
                    System.out.println("Task id and description required.");
                    return;
                }

                if (args.length > 3) {
                    System.out.println("Too many arguments for update.");
                    return;
                }

                Integer updateId = parseTaskId(args[1]);
                if (updateId == null) {
                    return;
                }

                if (isBlank(args[2])) {
                    System.out.println("Description required.");
                    return;
                }

                service.updateTask(updateId, args[2]);
                break;

            case "delete":
                if (args.length < 2) {
                    System.out.println("Task id required.");
                    return;
                }

                if (args.length > 2) {
                    System.out.println("Too many arguments for delete.");
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

                if (args.length > 2) {
                    System.out.println("Too many arguments for mark-in-progress.");
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

                
                if (args.length > 2) {
                    System.out.println("Too many arguments for mark-done.");
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
            int taskId = Integer.parseInt(rawId);
            if (taskId <= 0) {
                System.out.println("Invalid task id.");
                return null;
            }
            return taskId;
        } catch (NumberFormatException e) {
            System.out.println("Invalid task id.");
            return null;
        }
    }

    private static boolean isValidStatus(String status) {
        return "todo".equals(status)
                || "in-progress".equals(status)
                || "done".equals(status);
    }

    private static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}