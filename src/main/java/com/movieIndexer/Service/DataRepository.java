package com.movieIndexer.Service;

import com.movieIndexer.Model.Filme;
import com.movieIndexer.Model.HashMap; // HashMap customizado

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class DataRepository {

    private static DataRepository instance;

    private final HashMap filmMap;
    private final JsonManagerService jsonService;
    private final AtomicLong lastId;

    private static final String DATA_PATH = "Data/filmes.json";
    private static final String ID_PATH = "Data/user.json";

    private DataRepository() {
        this.filmMap = new HashMap();
        this.jsonService = new JsonManagerService(DATA_PATH, ID_PATH);

        try {
            this.lastId = new AtomicLong(jsonService.loadLastId());
            List<Map.Entry<Integer, Filme>> entries = jsonService.loadData();
            for (Map.Entry<Integer, Filme> entry : entries) { filmMap.put(entry.getKey(), entry.getValue()); }
            System.out.println("DataRepository inicializado. " + filmMap.size() + " filmes carregados.");
        } catch (Exception e) {
            System.err.println("Falha crítica ao inicializar DataRepository: " + e.getMessage());
            throw new RuntimeException("Não foi possível carregar os dados", e);
        }
    }

    public static DataRepository getInstance() {
        if (instance == null) { synchronized (DataRepository.class) { if (instance == null) instance = new DataRepository(); } }
        return instance;
    }

    public HashMap getFilmesMap() { return filmMap; }

    public synchronized void saveFilmes() {
        try {
            List<Filme> filmes = filmMap.getAllFilmes();
            jsonService.saveData(filmes);
        } catch (Exception e) { System.err.println("Erro ao salvar filmes: " + e.getMessage()); }
    }

    public int getNextId() {
        long next = lastId.incrementAndGet();
        jsonService.saveLastId(next);
        return (int) next;
    }
}
