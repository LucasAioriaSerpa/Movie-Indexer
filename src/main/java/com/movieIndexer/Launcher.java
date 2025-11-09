package com.movieIndexer;

import atlantafx.base.theme.PrimerDark;
import com.movieIndexer.View.MenuView;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class Launcher extends Application {
    public static void main(String[] args) { launch(args); }
    @Override
    public void start(Stage stage) throws IOException {
        Application.setUserAgentStylesheet(new PrimerDark().getUserAgentStylesheet());
        MenuView menuView = new MenuView();
        menuView.start(stage);
    }
}
