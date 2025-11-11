package com.movieIndexer;

import atlantafx.base.theme.PrimerDark;
import com.movieIndexer.Service.DataRepository;
import com.movieIndexer.View.SceneManager;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class Launcher extends Application {
    public static void main(String[] args) {
        DataRepository.getInstance();
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        Application.setUserAgentStylesheet(new PrimerDark().getUserAgentStylesheet());
        SceneManager sceneManager = new SceneManager(stage);
        sceneManager.showMenu();
    }
}