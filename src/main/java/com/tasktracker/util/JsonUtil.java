package com.tasktracker.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.tasktracker.model.Task;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonUtil {

    private static final ObjectMapper mapper = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT);


    public static List<Task> readTasks(String fileName) {

        File file = new File(fileName);

        if (!file.exists()) {
            return new ArrayList<>();
        }

        try {
            return mapper.readValue(
                    file,
                    new TypeReference<List<Task>>() {}
            );
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    public static void writeTasks(String fileName, List<Task> tasks) {

        try {
            mapper.writeValue(new File(fileName), tasks);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}