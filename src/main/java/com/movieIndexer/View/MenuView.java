package com.movieIndexer.View;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class MenuView{
    public void start(Stage stage) throws IOException {
        URL fxmlLocation = getClass().getResource("/com/movieIndexer/menu-view.fxml");
        if (fxmlLocation == null) {
            System.err.println("Erro: Arquivo FXML não encontrado no caminho /com/movieIndexer/menu-view.fxml");
            throw new IOException("Recurso FXML não encontrado.");
        }
        FXMLLoader fxmlLoader = new FXMLLoader(fxmlLocation);
        Scene scene = new Scene(fxmlLoader.load(), 1280, 960);
        stage.setTitle("Movie Indexer - MENU");
        stage.setScene(scene);
        stage.show();
    }
}
