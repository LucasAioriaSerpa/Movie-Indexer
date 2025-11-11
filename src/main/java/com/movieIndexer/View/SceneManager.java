package com.movieIndexer.View;

// Assumindo que a classe principal 'App' está aqui
import com.movieIndexer.Controller.InserirFilmeController;
import com.movieIndexer.Launcher;
import com.movieIndexer.Model.Filme;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class SceneManager {

    private final Stage stage;
    private Scene menuScene;

    public SceneManager(Stage stage) {
        this.stage = stage;
        stage.setTitle("Movie Indexer");
        stage.setWidth(1280);
        stage.setHeight(960);
    }

    private Parent loadFXML(String fxmlFile) throws IOException {
        String fxmlPath = "/com/movieIndexer/" + fxmlFile;
        return FXMLLoader.load(Objects.requireNonNull(Launcher.class.getResource(fxmlPath)));
    }

    private FXMLLoader getFXMLLoader(String fxmlFile) {
        String fxmlPath = "/com/movieIndexer/" + fxmlFile;
        return new FXMLLoader(Launcher.class.getResource(fxmlPath));
    }

    public void showScene(Parent root) {
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void showMenu() {
        try {
            if (menuScene == null) {
                FXMLLoader loader = getFXMLLoader("menu-view.fxml");
                Parent root = loader.load();
                com.movieIndexer.Controller.MenuController controller = loader.getController();
                controller.setSceneManager(this);
                menuScene = new Scene(root);
            }
            stage.setScene(menuScene);
            stage.show();
        } catch (IOException e) { e.printStackTrace(); }
    }

    public void showInserirFilme() { showInserirFilme(null, -1); }

    public void showInserirFilme(Filme filme, int id) {
        try {
            FXMLLoader loader = getFXMLLoader("inserir-filme-view.fxml");
            Parent root = loader.load();
            InserirFilmeController controller = loader.getController();
            controller.setSceneManager(this);
            if (filme != null) { controller.loadFilmeForEdit(filme, id); }
            showScene(root);
        } catch (IOException e) { e.printStackTrace(); }
    }

    public void showBuscarFilmes() {
        try {
            FXMLLoader loader = getFXMLLoader("buscar-filme-view.fxml");
            Parent root = loader.load();
            com.movieIndexer.Controller.BuscarFilmeController controller = loader.getController();
            controller.setSceneManager(this);
            showScene(root);
        } catch (IOException e) { e.printStackTrace(); }
    }

    public void showRemoverFilme() {
        try {
            FXMLLoader loader = getFXMLLoader("remover-filme-view.fxml");
            Parent root = loader.load();
            com.movieIndexer.Controller.RemoverFilmeController controller = loader.getController();
            controller.setSceneManager(this);
            showScene(root);
        } catch (IOException e) { e.printStackTrace(); }
    }

    public void showOpcoes() {
        try {
            FXMLLoader loader = getFXMLLoader("opcoes-view.fxml");
            Parent root = loader.load();
            com.movieIndexer.Controller.OpcoesController controller = loader.getController();
            controller.setSceneManager(this);
            showScene(root);
        } catch (IOException e) { e.printStackTrace(); }
    }
}