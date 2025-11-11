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
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class BuscarFilmeController {

    @FXML private TableView<Filme> tableViewFilmes;
    @FXML private TableColumn<Filme, Integer> colId;
    @FXML private TableColumn<Filme, String> colTitulo;
    @FXML private TableColumn<Filme, String> colDiretor;
    @FXML private TableColumn<Filme, Integer> colAno;
    @FXML private TableColumn<Filme, Integer> colDuracao;
    @FXML private TableColumn<Filme, String> colSinopse;
    @FXML private ComboBox<String> comboStrategy;
    @FXML private ComboBox<String> comboComparator;
    @FXML private TextField fieldFiltroTitulo;
    @FXML private TextField fieldFiltroDiretor;
    @FXML private TextField fieldAnoInicial;
    @FXML private TextField fieldAnoFinal;
    @FXML private TextField fieldDuracaoMin;
    @FXML private TextField fieldDuracaoMax;
    @FXML private Label lblTotalFilmes;

    private HashMap meuHashMap;
    private ConfigManagerService configService;
    private UserConfig preferenciasUsuario;
    private List<Filme> filmesOrdenadosCache = new ArrayList<>();

    private static final String CONFIG_PATH = "Data/config.json";
    private SceneManager sceneManager;

    @FXML
    public void initialize() {
        configService = new ConfigManagerService(CONFIG_PATH);
        preferenciasUsuario = configService.loadConfig();
        meuHashMap = carregarFilmesDoDisco();
        configurarTabela();
        configurarCombos();
        configurarFiltros();
        atualizarFilmesOrdenados();
    }

    @FXML
    private void handleVoltar() { sceneManager.showMenu(); }

    @FXML
    private void handleLimparFiltros() {
        fieldFiltroTitulo.clear();
        fieldFiltroDiretor.clear();
        fieldAnoInicial.clear();
        fieldAnoFinal.clear();
        fieldDuracaoMin.clear();
        fieldDuracaoMax.clear();
        atualizarFilmesFiltrados();
    }

    @FXML
    private void handleRestaurarPreferencias() {
        preferenciasUsuario = configService.loadConfig();
        comboStrategy.setValue(SortProvider.getKeyFromValue(SortProvider.STRATEGY_OPTIONS, preferenciasUsuario.getSortingStrategy()));
        comboComparator.setValue(SortProvider.getKeyFromValue(SortProvider.COMPARATOR_OPTIONS, preferenciasUsuario.getSortingComparator()));
        atualizarFilmesOrdenados();
    }

    public void setSceneManager(SceneManager sceneManager) { this.sceneManager = sceneManager; }

    private void configurarTabela() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        colDiretor.setCellValueFactory(new PropertyValueFactory<>("diretor"));
        colAno.setCellValueFactory(new PropertyValueFactory<>("anoLancamento"));
        colDuracao.setCellValueFactory(new PropertyValueFactory<>("duracaoEmMinutos"));
        colSinopse.setCellValueFactory(new PropertyValueFactory<>("sinopse"));
    }

    private void configurarCombos() {
        comboStrategy.setItems(FXCollections.observableArrayList(SortProvider.STRATEGY_OPTIONS.keySet()));
        comboComparator.setItems(FXCollections.observableArrayList(SortProvider.COMPARATOR_OPTIONS.keySet()));

        comboStrategy.setValue(SortProvider.getKeyFromValue(SortProvider.STRATEGY_OPTIONS, preferenciasUsuario.getSortingStrategy()));
        comboComparator.setValue(SortProvider.getKeyFromValue(SortProvider.COMPARATOR_OPTIONS, preferenciasUsuario.getSortingComparator()));

        comboStrategy.valueProperty().addListener((obs, oldValue, newValue) -> atualizarFilmesOrdenados());
        comboComparator.valueProperty().addListener((obs, oldValue, newValue) -> atualizarFilmesOrdenados());
    }

    private void configurarFiltros() {
        fieldFiltroTitulo.textProperty().addListener((obs, oldValue, newValue) -> atualizarFilmesFiltrados());
        fieldFiltroDiretor.textProperty().addListener((obs, oldValue, newValue) -> atualizarFilmesFiltrados());
        fieldAnoInicial.textProperty().addListener((obs, oldValue, newValue) -> atualizarFilmesFiltrados());
        fieldAnoFinal.textProperty().addListener((obs, oldValue, newValue) -> atualizarFilmesFiltrados());
        fieldDuracaoMin.textProperty().addListener((obs, oldValue, newValue) -> atualizarFilmesFiltrados());
        fieldDuracaoMax.textProperty().addListener((obs, oldValue, newValue) -> atualizarFilmesFiltrados());
    }

    private void atualizarFilmesOrdenados() {
        String strategyKey = comboStrategy.getValue();
        String comparatorKey = comboComparator.getValue();

        String strategyName = strategyKey != null
                ? SortProvider.STRATEGY_OPTIONS.get(strategyKey)
                : preferenciasUsuario.getSortingStrategy();

        String comparatorName = comparatorKey != null
                ? SortProvider.COMPARATOR_OPTIONS.get(comparatorKey)
                : preferenciasUsuario.getSortingComparator();

        SortStrategy strategy = SortProvider.getStrategy(strategyName);
        Comparator<Map.Entry<Integer, Filme>> comparator = SortProvider.getComparator(comparatorName);

        List<Filme> filmesOrdenados = SortService.exportAndSortToList(meuHashMap, strategy, comparator);
        filmesOrdenadosCache = new ArrayList<>(filmesOrdenados);
        sincronizarTabela(filmesOrdenadosCache);
    }

    private void atualizarFilmesFiltrados() {
        sincronizarTabela(filmesOrdenadosCache);
    }

    private void sincronizarTabela(List<Filme> base) {
        Objects.requireNonNull(base, "Lista base não pode ser nula");
        List<Filme> filtrados = aplicarFiltros(base);
        tableViewFilmes.setItems(FXCollections.observableArrayList(filtrados));
        atualizarStatus(filtrados.size());
    }

    private List<Filme> aplicarFiltros(List<Filme> filmes) {
        if (filmes == null || filmes.isEmpty()) {
            return List.of();
        }

        String tituloFiltro = normalizar(fieldFiltroTitulo.getText());
        String diretorFiltro = normalizar(fieldFiltroDiretor.getText());
        Integer anoInicial = parseInteger(fieldAnoInicial.getText());
        Integer anoFinal = parseInteger(fieldAnoFinal.getText());
        Integer duracaoMin = parseInteger(fieldDuracaoMin.getText());
        Integer duracaoMax = parseInteger(fieldDuracaoMax.getText());

        return filmes.stream()
                .filter(filme -> correspondeTexto(filme.getTitulo(), tituloFiltro))
                .filter(filme -> correspondeTexto(filme.getDiretor(), diretorFiltro))
                .filter(filme -> correspondeIntervalo(filme.getAnoLancamento(), anoInicial, anoFinal))
                .filter(filme -> correspondeIntervalo(filme.getDuracaoEmMinutos(), duracaoMin, duracaoMax))
                .collect(Collectors.toList());
    }

    private void atualizarStatus(int quantidade) {
        if (lblTotalFilmes != null) {
            String sufixo = quantidade == 1 ? "" : "s";
            lblTotalFilmes.setText(quantidade + " filme" + sufixo + " encontrado" + sufixo + ".");
        }
    }

    private String normalizar(String texto) {
        return texto == null ? "" : texto.trim().toLowerCase();
    }

    private boolean correspondeTexto(String valorOriginal, String filtroNormalizado) {
        if (filtroNormalizado == null || filtroNormalizado.isEmpty()) {
            return true;
        }
        String base = valorOriginal == null ? "" : valorOriginal.toLowerCase();
        return base.contains(filtroNormalizado);
    }

    private boolean correspondeIntervalo(int valor, Integer minimo, Integer maximo) {
        if (minimo != null && valor < minimo) {
            return false;
        }
        if (maximo != null && valor > maximo) {
            return false;
        }
        return true;
    }

    private Integer parseInteger(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private HashMap carregarFilmesDoDisco() {
        return DataRepository.getInstance().getFilmesMap();
    }
}