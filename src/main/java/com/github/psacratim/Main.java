package com.github.psacratim;

import com.github.psacratim.database.DatabaseManager;
import com.github.psacratim.database.TaskStatus;

import java.io.File;
import java.nio.file.Files;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    private static void checkForArgs(String[] args, int neededArgsSize, String errorMessage){
        if (args.length < neededArgsSize) {
            System.err.println(errorMessage);
            System.exit(-150);
        }
    }

    public static void main(String[] args) {
        DatabaseManager databaseManager = new DatabaseManager();
        checkForArgs(args, 1, """
                All commands availables:
                    # Adding a new task
                    task-cli add "Buy groceries"
                    # Output: Task added successfully (ID: 1)
                    
                    # Updating and deleting tasks
                    task-cli update 1 "Buy groceries and cook dinner"
                    task-cli delete 1
                    
                    # Marking a task as in progress or done
                    task-cli mark-in-progress 1
                    task-cli mark-done 1
                    
                    # Listing all tasks
                    task-cli list
                    
                    # Listing tasks by status
                    task-cli list done
                    task-cli list todo
                    task-cli list in-progress
                """);

        switch (args[0]){
            case "add": {
                checkForArgs(args, 2, "Invalid arguments, use: task-cli add \"Your task\"");

                String taskInfo = args[1];

                var addedTask = databaseManager.addTask(TaskStatus.TODO, taskInfo);
                if (addedTask != null) {
                    System.out.println("Task added successfully (ID: " + addedTask.getId() + ")");
                }
                break;
            }

            case "delete": {
                int taskId = Integer.parseInt(args[1]);

                databaseManager.removeTask(taskId);
                break;
            }

            case "update": {
                checkForArgs(args, 3, "Invalid arguments, use: task-cli add \"Your task\"");

                String newInfo = args[2];
                int taskId = Integer.parseInt(args[1]);

                databaseManager.setInfo(taskId, newInfo);
                break;
            }

            case "mark-in-progress": {
                checkForArgs(args, 2, "Invalid arguments, use: task-cli add \"Your task\"");

                int taskId = Integer.parseInt(args[1]);
                databaseManager.setStatus(taskId, TaskStatus.IN_PROGRESS);
                break;
            }

            case "mark-done": {
                checkForArgs(args, 2, "Invalid arguments, use: task-cli add \"Your task\"");

                int taskId = Integer.parseInt(args[1]);
                databaseManager.setStatus(taskId, TaskStatus.DONE);
                break;
            }

            case "list": {
                System.out.println("=========== YOUR TASKS ===========");
                if (args.length >= 2) {
                    String subArg = args[1];

                    switch (subArg) {
                        case "done":
                            databaseManager.getAllTasks().values().stream().filter(task -> task.getStatus() == TaskStatus.DONE).forEach(task -> {
                                System.out.printf("[%s] %s (%d)%n", task.getStatus().getStatusPrefix(), task.getTask(), task.getId());
                            });
                            break;

                        case "in-progress":
                            databaseManager.getAllTasks().values().stream().filter(task -> task.getStatus() == TaskStatus.IN_PROGRESS).forEach(task -> {
                                System.out.printf("[%s] %s (%d)%n", task.getStatus().getStatusPrefix(), task.getTask(), task.getId());
                            });
                            break;

                        case "todo":
                            databaseManager.getAllTasks().values().stream().filter(task -> task.getStatus() == TaskStatus.TODO).forEach(task -> {
                                System.out.printf("[%s] %s (%d)%n", task.getStatus().getStatusPrefix(), task.getTask(), task.getId());
                            });
                            break;
                    }
                } else {
                    databaseManager.getAllTasks().forEach((taskId, task) -> {
                        System.out.printf("[%s] %s (%d)%n", task.getStatus().getStatusPrefix(), task.getTask(), task.getId());
                    });
                }
                System.out.println("==================================");
                break;
            }
        }
    }
}