package com.github.psacratim.database;

import org.json.JSONObject;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class DatabaseManager {

    private Path dbPath;
    private JSONObject dbJson;
    private HashMap<Integer, Task> allTasks = new HashMap<>();

    public DatabaseManager(){
        File database = new File("./database.json");

        if (!database.exists()){
            try {
                boolean hasCreated = database.createNewFile();
                if (!hasCreated){
                    System.err.println("Error on create database.json on running path.");
                    return;
                }
            } catch (IOException e) {
                System.err.println("Exception on create database.json:");
                throw new RuntimeException(e);
            }
        }

        this.dbPath = database.toPath();
        try {
            String jsonString = String.join("", Files.readAllLines(this.dbPath));
            if (jsonString.isEmpty())
                jsonString = "{}";

            this.dbJson = new JSONObject(jsonString);
            save();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.dbJson.toMap().forEach((key, value) -> {
            if (value instanceof HashMap<?,?> hashmap){
                var taskId = Integer.parseInt(key);
                var task   = new Task(taskId, TaskStatus.getFromId((int)hashmap.get("status")), (String)hashmap.get("info"));

                allTasks.put(taskId, task);
            }
        });
    }

    public Task addTask(TaskStatus status, String taskInfo){
        var id = allTasks.size() + 1;
        var task = new Task(id, status, taskInfo);

        this.dbJson.put(String.valueOf(id), Map.ofEntries(
                Map.entry("status", status.getStatusCode()),
                Map.entry("info", taskInfo)
        ));

        allTasks.put(id, task);
        save();

        return task;
    }

    public void setStatus(int id, TaskStatus status){
        this.dbJson.getJSONObject(String.valueOf(id)).put("status", status.getStatusCode());
        save();
    }

    public void removeTask(int id){
        this.dbJson.remove(String.valueOf(id));
        save();
    }

    public void save(){
        try {
            FileOutputStream outputStream = new FileOutputStream(this.dbPath.toFile());

            outputStream.write(this.dbJson.toString().getBytes());

            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            System.err.println("Exception on save json data in database file:");
            throw new RuntimeException(e);
        }

    }

    public void setInfo(int id, String info) {
        this.dbJson.getJSONObject(String.valueOf(id)).put("info", info);
        save();
    }

    public HashMap<Integer, Task> getAllTasks() {
        return allTasks;
    }
}
