package com.github.psacratim.database;

public class Task {

    private int id;
    private TaskStatus status; // 0 = TO-DO, 1 = In Progress, 2 = Done
    private String task;

    public Task(int id, TaskStatus status, String task){
        this.id = id;
        this.status = status;
        this.task = task;
    }

    public int getId() {
        return id;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public String getTask() {
        return task;
    }
}
