package com.movieIndexer.Controller;

import com.movieIndexer.Model.Filme;
import com.movieIndexer.Model.HashMap;
import com.movieIndexer.Model.Sorts.SortStrategy;
import com.movieIndexer.Model.UserConfig;
import com.movieIndexer.Service.ConfigManagerService;
import com.movieIndexer.Service.DataRepository;
import com.movieIndexer.Service.SortService;
import com.movieIndexer.Utils.SortProvider;
import com.movieIndexer.View.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class BuscarFilmeController {

    @FXML private ListView<String> movieListView;
    @FXML private TableView<Filme> tableViewFilmes;
    @FXML private TableColumn<Filme, Integer> colId;
    @FXML private TableColumn<Filme, String> colTitulo;
    @FXML private TableColumn<Filme, String> colDiretor;
    @FXML private TableColumn<Filme, Integer> colAno;
    @FXML private TableColumn<Filme, Integer> colDuracao;
    @FXML private TableColumn<Filme, String> colSinopse;

    private HashMap meuHashMap;
    private ConfigManagerService configService;
    private static final String CONFIG_PATH = "Data/config.json";
    private SceneManager sceneManager;

    @FXML
    public void initialize() {
        configService = new ConfigManagerService(CONFIG_PATH);
        meuHashMap = carregarFilmesDoDisco();
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        colDiretor.setCellValueFactory(new PropertyValueFactory<>("diretor"));
        colAno.setCellValueFactory(new PropertyValueFactory<>("anoLancamento"));
        colDuracao.setCellValueFactory(new PropertyValueFactory<>("duracaoEmMinutos"));
        colSinopse.setCellValueFactory(new PropertyValueFactory<>("sinopse"));
        atualizarFilmesOrdenados();
    }

    @FXML
    private void handleVoltar() { sceneManager.showMenu(); }

    public void setSceneManager(SceneManager sceneManager) { this.sceneManager = sceneManager; }

    private void atualizarFilmesOrdenados() {
        UserConfig config = configService.loadConfig();
        SortStrategy strategy = SortProvider.getStrategy(config.getSortingStrategy());
        Comparator<Map.Entry<Integer, Filme>> comparator = SortProvider.getComparator(config.getSortingComparator());
        List<Filme> filmesOrdenados = SortService.exportAndSortToList(meuHashMap, strategy, comparator);
        tableViewFilmes.getItems().clear();
        tableViewFilmes.getItems().addAll(filmesOrdenados);
    }

    private HashMap carregarFilmesDoDisco() { return DataRepository.getInstance().getFilmesMap(); }

}