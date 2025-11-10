module com.movieIndexer {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires atlantafx.base;
    requires com.google.gson;

    exports com.movieIndexer;
    exports com.movieIndexer.View;
    exports com.movieIndexer.Controller;

    opens com.movieIndexer              to com.google.gson, javafx.fxml, javafx.graphics, javafx.controls;
    opens com.movieIndexer.View         to com.google.gson, javafx.fxml, javafx.graphics, javafx.controls;
    opens com.movieIndexer.Controller   to com.google.gson, javafx.fxml, javafx.graphics, javafx.controls;

}