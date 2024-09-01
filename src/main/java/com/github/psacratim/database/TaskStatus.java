package com.github.psacratim.database;

public enum TaskStatus {

    TODO(0, "TO-DO"),
    IN_PROGRESS(1, "In Progress"),
    DONE(2, "Done!");

    private int statusCode;
    private String statusPrefix;

    TaskStatus(int statusCode, String statusPrefix){
        this.statusCode = statusCode;
        this.statusPrefix = statusPrefix;
    }

    public String getStatusPrefix() {
        return statusPrefix;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public static TaskStatus getFromId(int id){
        for (TaskStatus value : TaskStatus.values()) {
            if (value.getStatusCode() == id){
                return value;
            }
        }

        return null;
    }
}
