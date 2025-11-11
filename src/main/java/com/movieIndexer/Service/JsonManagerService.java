package com.movieIndexer.Service;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.movieIndexer.Model.Filme;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JsonManagerService {

    private final String dataFilePath;
    private final String idFilePath;
    private final Gson gson;
    private final Type filmListType;

    public JsonManagerService(String dataFilePath, String idFilePath) {
        this.dataFilePath = dataFilePath;
        this.idFilePath = idFilePath;
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.filmListType = new TypeToken<List<Filme>>() {}.getType();
    }

    public void saveData(List<Filme> filmes) {
        try {
            Files.createDirectories(Paths.get(dataFilePath).getParent());
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(dataFilePath))) { gson.toJson(filmes, filmListType, writer); }
        } catch (IOException e) { throw new RuntimeException("Falha ao salvar dados em " + dataFilePath, e); }
    }

    public List<Map.Entry<Integer, Filme>> loadData() {
        if (!Files.exists(Paths.get(dataFilePath))) return List.of();
        try (Reader reader = new FileReader(dataFilePath)) {
            List<Filme> loadedFilmes = gson.fromJson(reader, filmListType);
            List<Map.Entry<Integer, Filme>> entries = new ArrayList<>();
            if (loadedFilmes != null) {
                for (Filme filme : loadedFilmes) { entries.add(new AbstractMap.SimpleEntry<>(filme.getId(), filme)); }
            }
            return entries;
        } catch (IOException e) {
            throw new RuntimeException("Falha ao ler dados de " + dataFilePath, e);
        }
    }

    public long loadLastId() {
        if (!Files.exists(Paths.get(idFilePath))) return 0L;
        try {
            String idStr = new String(Files.readAllBytes(Paths.get(idFilePath))).trim();
            if (idStr.isEmpty()) return 0L;
            return Long.parseLong(idStr);
        } catch (IOException | NumberFormatException e) {
            System.err.println("Aviso: Não foi possível ler o lastId. Redefinindo para 0. Erro: " + e.getMessage());
            return 0L;
        }
    }

    public void saveLastId(long lastId) {
        try {
            Files.createDirectories(Paths.get(idFilePath).getParent());
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(idFilePath))) { writer.write(Long.toString(lastId)); }
        } catch (IOException e) { throw new RuntimeException("Falha ao salvar lastId em " + idFilePath, e); }
    }
}
