package com.movieIndexer.Service;

import com.movieIndexer.Model.BulkMovieTemplateProvider;
import com.movieIndexer.Model.Filme;
import com.movieIndexer.Model.HashMap;
import com.movieIndexer.Model.UserConfig;
import com.movieIndexer.Utils.LoggingManager;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class DataRepository {

    private static final LoggingManager LOG = new LoggingManager();
    private static final int BULK_TEMPLATE_SIZE = 700; //! 3000 MAX SIZE

    private static DataRepository instance;

    private final HashMap filmMap;
    private final JsonManagerService jsonService;
    private final ConfigManagerService configManager;
    private final AtomicLong lastId;

    private static final String DATA_PATH = "Data/filmes.json";
    private static final String ID_PATH = "Data/user.json";
    private static final String CONFIG_PATH = "Data/config.json";

    private DataRepository() {
        this.filmMap = new HashMap();
        this.jsonService = new JsonManagerService(DATA_PATH, ID_PATH);
        this.configManager = new ConfigManagerService(CONFIG_PATH);

        try {
            UserConfig userConfig = configManager.loadConfig();
            this.lastId = new AtomicLong(jsonService.loadLastId());

            List<Map.Entry<Integer, Filme>> entries = carregarOuPopular(userConfig);
            for (Map.Entry<Integer, Filme> entry : entries) {
                filmMap.put(entry.getKey(), entry.getValue());
            }

            LOG.logInfo("100", "DataRepository inicializado com " + filmMap.size() + " filmes em memória.");
        } catch (Exception e) {
            LOG.logError("404", "Falha crítica ao inicializar DataRepository.", e);
            throw new RuntimeException("Não foi possível carregar os dados", e);
        }
    }

    public static DataRepository getInstance() {
        if (instance == null) {
            synchronized (DataRepository.class) {
                if (instance == null) {
                    instance = new DataRepository();
                }
            }
        }
        return instance;
    }

    public HashMap getFilmesMap() {
        return filmMap;
    }

    public synchronized void saveFilmes() {
        try {
            List<Filme> filmes = filmMap.getAllFilmes();
            jsonService.saveData(filmes);
            LOG.logInfo("200", "Persistidos " + filmes.size() + " filmes em " + DATA_PATH + ".");
        } catch (Exception e) {
            LOG.logError("404", "Erro ao salvar filmes.", e);
        }
    }

    public int getNextId() {
        long next = lastId.incrementAndGet();
        jsonService.saveLastId(next);
        LOG.logInfo("100", "Gerado novo ID para filme: " + next + ".");
        return (int) next;
    }

    private List<Map.Entry<Integer, Filme>> carregarOuPopular(UserConfig config) {
        if (config != null && config.isBulkInsertTemplateEnabled()) {
            LOG.logInfo("100", "Flag de template detectada. Gerando " + BULK_TEMPLATE_SIZE + " filmes fictícios.");
            List<Map.Entry<Integer, Filme>> entries = gerarTemplateFilmes();
            config.setBulkInsertTemplateEnabled(false);
            try {
                configManager.saveConfig(config);
                LOG.logInfo("200", "Flag de template desativada automaticamente no arquivo de configuração.");
            } catch (RuntimeException e) {
                LOG.logWarning("303", "Não foi possível atualizar o arquivo de configuração após o bulk insert.", e);
            }
            return entries;
        }

        List<Map.Entry<Integer, Filme>> entries = jsonService.loadData();
        LOG.logInfo("100", "Carregados " + entries.size() + " filmes do disco.");
        return entries;
    }

    private List<Map.Entry<Integer, Filme>> gerarTemplateFilmes() {
        List<Filme> filmesTemplate = BulkMovieTemplateProvider.gerarFilmes(BULK_TEMPLATE_SIZE);
        List<Map.Entry<Integer, Filme>> entries = new ArrayList<>(filmesTemplate.size());

        for (Filme filme : filmesTemplate) {
            entries.add(new AbstractMap.SimpleEntry<>(filme.getId(), filme));
        }

        jsonService.saveData(filmesTemplate);

        long ultimoId = filmesTemplate.isEmpty()
                ? lastId.get()
                : filmesTemplate.get(filmesTemplate.size() - 1).getId();

        lastId.set(ultimoId);
        jsonService.saveLastId(ultimoId);

        LOG.logInfo("200", "Template com " + filmesTemplate.size() + " filmes salvo em " + DATA_PATH + ".");
        return entries;
    }
}
