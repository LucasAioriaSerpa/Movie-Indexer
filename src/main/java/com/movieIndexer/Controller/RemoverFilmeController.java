package com.movieIndexer.Controller;

import com.movieIndexer.Model.Filme;
import com.movieIndexer.Service.DataRepository;
import com.movieIndexer.View.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class RemoverFilmeController {

    @FXML private TextField fieldId;
    @FXML private Button btnRemover;
    @FXML private Label lblStatus;

    private SceneManager sceneManager;
    private DataRepository dataRepository;

    public void setSceneManager(SceneManager sceneManager) { this.sceneManager = sceneManager; }

    @FXML
    public void initialize() { this.dataRepository = DataRepository.getInstance(); }

    @FXML
    private void handleRemover() {
        try {
            int id = Integer.parseInt(fieldId.getText());
            Filme filmeRemovido = (Filme) dataRepository.getFilmesMap().remove(id);
            if (filmeRemovido != null) {
                dataRepository.saveFilmes();
                showStatus("Filme '" + filmeRemovido.getTitulo() + "' (ID: " + id + ") foi removido.", true);
                fieldId.clear();
            } else { showStatus("Erro: Nenhum filme encontrado com o ID: " + id, false); }
        } catch (NumberFormatException e) { showStatus("Erro: O ID deve ser um número.", false); }
        catch (Exception e) { showStatus("Erro inesperado: " + e.getMessage(), false); }
    }

    @FXML
    private void handleVoltar() { sceneManager.showMenu(); }

    private void showStatus(String message, boolean isSuccess) {
        lblStatus.setText(message);
        lblStatus.setTextFill(isSuccess ? Color.GREEN : Color.RED);
        lblStatus.setVisible(true);
    }
}