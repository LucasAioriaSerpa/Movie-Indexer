package com.movieIndexer.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicLong;

public class JsonManagerService {

    private final String dataFilePath;
    private final String idFilePath;
    private final Gson gson;
    private final Type listType;
    private final AtomicLong lastId;

    public JsonManagerService(String dataFilePath, String idFilePath, Type listType) {
        this.dataFilePath = dataFilePath;
        this.idFilePath = idFilePath;
        this.listType = listType;
        this.lastId = new AtomicLong(0);
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    private long loadLastId() {

    }

    private void saveLastId() {
        try {
            Files.createDirectories(Paths.get(idFilePath).getParent());
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(idFilePath))) { writer.write(Long.toString(lastId.get())); }
        } catch (IOException e) {
            throw new RuntimeException(e); //adicionar logger aqui
        }
    }

}
