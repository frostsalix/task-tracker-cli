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

            default:
                System.out.println("Unknown command.");
        }
    }
}