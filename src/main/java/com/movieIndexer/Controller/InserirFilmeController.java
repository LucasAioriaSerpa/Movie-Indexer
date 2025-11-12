package com.movieIndexer.Controller;

import com.movieIndexer.Model.Filme;
import com.movieIndexer.Service.DataRepository;
import com.movieIndexer.View.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.time.LocalDate;

public class InserirFilmeController {

    @FXML private Text txtTitulo;
    @FXML private TextField fieldTitulo;
    @FXML private TextArea areaSinopse;
    @FXML private TextField fieldDiretor;
    @FXML private Spinner<Integer> spinnerAno;
    @FXML private TextField fieldDuracao;
    @FXML private Button btnSalvar;
    @FXML private Label lblStatus;
    @FXML private Label lblFilmeId;

    private SceneManager sceneManager;
    private DataRepository dataRepository;
    private boolean isEditMode = false;

    public void setSceneManager(SceneManager sceneManager) { this.sceneManager = sceneManager; }

    @FXML
    public void initialize() {
        this.dataRepository = DataRepository.getInstance();
        int currentYear = LocalDate.now().getYear();
        SpinnerValueFactory<Integer> yearFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1888, currentYear, currentYear - 1);
        spinnerAno.setValueFactory(yearFactory);
        spinnerAno.setEditable(true);
    }

    public void loadFilmeForEdit(Filme filme, int id) {
        isEditMode = true;

        fieldTitulo.setText(filme.getTitulo());
        areaSinopse.setText(filme.getSinopse());
        fieldDiretor.setText(filme.getDiretor());
        spinnerAno.getValueFactory().setValue(filme.getAnoLancamento());
        fieldDuracao.setText(String.valueOf(filme.getDuracaoEmMinutos()));

        lblFilmeId.setText(String.valueOf(id));
        txtTitulo.setText("Editar Filme");
        btnSalvar.setText("Atualizar Filme");
    }

    @FXML
    private void handleSalvar() {
        try {
            if (fieldTitulo.getText().isEmpty() || fieldDiretor.getText().isEmpty()) {
                showStatus("Título e Diretor são obrigatórios.", false);
                return;
            }

            Filme filme = new Filme();
            filme.setTitulo(fieldTitulo.getText());
            filme.setSinopse(areaSinopse.getText());
            filme.setDiretor(fieldDiretor.getText());
            filme.setAnoLancamento(spinnerAno.getValue());

            long duracaoMin = Long.parseLong(fieldDuracao.getText().replaceAll("[^\\d]", ""));
            filme.setDuracaoEmMinutos((int) duracaoMin);

            int id;
            String successMessage;

            if (isEditMode) {
                id = Integer.parseInt(lblFilmeId.getText());
                successMessage = "Filme (ID: " + id + ") atualizado com sucesso!";
            } else {
                id = dataRepository.getNextId();
                successMessage = "Filme (ID: " + id + ") salvo com sucesso!";
            }

            filme.setId(id);
            dataRepository.getFilmesMap().put(id, filme);
            dataRepository.saveFilmes();
            showStatus(successMessage, true);
            if (!isEditMode) { clearForm(); }
        } catch (NumberFormatException e) { showStatus("Erro: Duração deve ser um número (ex: 120).", false); }
        catch (Exception e) {
            showStatus("Erro ao salvar: " + e.getMessage(), false);
            e.printStackTrace();
        }
    }

    @FXML
    private void handleVoltar() { sceneManager.showMenu(); }

    private void clearForm() {
        fieldTitulo.clear();
        areaSinopse.clear();
        fieldDiretor.clear();
        fieldDuracao.clear();
        spinnerAno.getValueFactory().setValue(LocalDate.now().getYear() - 1);
    }

    private void showStatus(String message, boolean isSuccess) {
        lblStatus.setText(message);
        lblStatus.setTextFill(isSuccess ? Color.GREEN : Color.RED);
        lblStatus.setVisible(true);
    }
}