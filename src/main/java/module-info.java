module com.movieIndexer {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;

    exports com.movieIndexer;
    exports com.movieIndexer.view;
    exports com.movieIndexer.controller;

    opens com.movieIndexer              to javafx.fxml, javafx.graphics, javafx.controls;
    opens com.movieIndexer.view         to javafx.fxml, javafx.graphics, javafx.controls;
    opens com.movieIndexer.controller   to javafx.fxml, javafx.graphics, javafx.controls;

}