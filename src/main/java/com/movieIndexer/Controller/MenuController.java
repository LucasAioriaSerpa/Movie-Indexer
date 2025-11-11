package com.movieIndexer.Controller;

import com.movieIndexer.View.SceneManager;
import javafx.application.Platform;
import javafx.fxml.FXML;

public class MenuController {

    private SceneManager sceneManager;

    public void setSceneManager(SceneManager sceneManager) { this.sceneManager = sceneManager; }

    @FXML
    private void handleInserir() { sceneManager.showInserirFilme(); }

    @FXML
    private void handleBuscar() { sceneManager.showBuscarFilmes(); }

    @FXML
    private void handleRemover() { sceneManager.showRemoverFilme(); }

    @FXML
    private void handleOpcoes() { sceneManager.showOpcoes(); }

    @FXML
    private void handleSair() { Platform.exit(); }
}