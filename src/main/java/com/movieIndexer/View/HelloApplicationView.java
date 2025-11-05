package com.movieIndexer.View;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class HelloApplicationView extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        URL fxmlLocation = getClass().getResource("/com/movieIndexer/hello-view.fxml");
        if (fxmlLocation == null) {
            System.err.println("Erro: Arquivo FXML não encontrado no caminho /com/movieIndexer/hello-view.fxml");
            throw new IOException("Recurso FXML não encontrado.");
        }
        FXMLLoader fxmlLoader = new FXMLLoader(fxmlLocation);
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }
}
