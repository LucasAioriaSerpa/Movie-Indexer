module com.movieIndexer {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires atlantafx.base;

    exports com.movieIndexer;
    exports com.movieIndexer.View;
    exports com.movieIndexer.Controller;

    opens com.movieIndexer              to javafx.fxml, javafx.graphics, javafx.controls;
    opens com.movieIndexer.View to javafx.fxml, javafx.graphics, javafx.controls;
    opens com.movieIndexer.Controller to javafx.fxml, javafx.graphics, javafx.controls;

}