package com.movieIndexer.Controller;

import com.movieIndexer.Model.UserConfig;
import com.movieIndexer.Service.ConfigManagerService;
import com.movieIndexer.Utils.SortProvider;
import com.movieIndexer.View.SceneManager;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class OpcoesController {

    @FXML
    private ComboBox<String> comboStrategy;
    @FXML
    private ComboBox<String> comboComparator;
    @FXML
    private CheckBox chkBulkTemplate;
    @FXML
    private Button btnSalvar;
    @FXML
    private Label lblStatus;

    private static final String CONFIG_PATH = "Data/config.json";
    private ConfigManagerService configService;
    private UserConfig config;
    private SceneManager sceneManager;

    @FXML
    public void initialize() {
        configService = new ConfigManagerService(CONFIG_PATH);
        comboStrategy.setItems(FXCollections.observableArrayList( SortProvider.STRATEGY_OPTIONS.keySet() ));
        comboComparator.setItems(FXCollections.observableArrayList( SortProvider.COMPARATOR_OPTIONS.keySet() ));
        loadSettings();
        lblStatus.setVisible(false);
    }

    public void setSceneManager(SceneManager sceneManager) { this.sceneManager = sceneManager; }

    private void loadSettings() {
        config = configService.loadConfig();
        String savedStrategyKey = SortProvider.getKeyFromValue( SortProvider.STRATEGY_OPTIONS, config.getSortingStrategy() );
        String savedComparatorKey = SortProvider.getKeyFromValue( SortProvider.COMPARATOR_OPTIONS, config.getSortingComparator() );
        comboStrategy.setValue(savedStrategyKey);
        comboComparator.setValue(savedComparatorKey);
        if (chkBulkTemplate != null) {
            chkBulkTemplate.setSelected(config.isBulkInsertTemplateEnabled());
        }
    }

    @FXML
    private void handleVoltar() { sceneManager.showMenu(); }

    @FXML
    private void handleSave() {
        try {
            String selectedStrategyKey = comboStrategy.getValue();
            String selectedComparatorKey = comboComparator.getValue();
            String strategyToSave = selectedStrategyKey != null
                    ? SortProvider.STRATEGY_OPTIONS.get(selectedStrategyKey)
                    : config.getSortingStrategy();
            String comparatorToSave = selectedComparatorKey != null
                    ? SortProvider.COMPARATOR_OPTIONS.get(selectedComparatorKey)
                    : config.getSortingComparator();
            config.setSortingStrategy(strategyToSave);
            config.setSortingComparator(comparatorToSave);
            if (chkBulkTemplate != null) {
                config.setBulkInsertTemplateEnabled(chkBulkTemplate.isSelected());
            }
            configService.saveConfig(config);
            lblStatus.setText("Preferências salvas com sucesso!");
            lblStatus.setTextFill(Color.GREEN);
            lblStatus.setVisible(true);
        } catch (Exception e) {
            lblStatus.setText("Erro ao salvar: " + e.getMessage());
            lblStatus.setTextFill(Color.RED);
            lblStatus.setVisible(true);
            e.printStackTrace();
        }
    }
}