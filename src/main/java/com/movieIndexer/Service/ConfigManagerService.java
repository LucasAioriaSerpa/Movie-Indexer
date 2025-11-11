package com.movieIndexer.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.movieIndexer.Model.UserConfig;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ConfigManagerService {

    private String configFilePath;
    private final Gson gson;

    public ConfigManagerService(String configFilePath) {
        this.configFilePath = configFilePath;
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    public void saveConfig(UserConfig userConfig) {
        try {
            Files.createDirectories(Paths.get(configFilePath).getParent());
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(configFilePath))) { gson.toJson(userConfig, UserConfig.class, writer); }
        } catch (IOException e) {
            System.err.println("Falha ao salvar a configuração do usuario. Erro: " + e.getMessage());
            throw  new RuntimeException("Falha ao salvar a configuração", e);
        }
    }

    public UserConfig loadConfig() {
        if (!Files.exists(Paths.get(configFilePath))) { return new UserConfig(); }
        try (Reader reader = new FileReader(configFilePath)) {
            UserConfig config = gson.fromJson(reader, UserConfig.class);
            if (config == null) { return new UserConfig(); }
            return config;
        } catch (IOException e) {
            System.err.println("Falha ao ler a configuração. Retornando padrão. Erro: " + e.getMessage());
            return new UserConfig();
        }
    }
}
