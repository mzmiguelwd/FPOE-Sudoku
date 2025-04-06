module org.example.fpoesudoku {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens org.example.fpoesudoku to javafx.fxml;

    opens org.example.fpoesudoku.models to javafx.fxml;
    exports org.example.fpoesudoku.views;
    opens org.example.fpoesudoku.views to javafx.fxml;
    exports org.example.fpoesudoku.controllers;
    opens org.example.fpoesudoku.controllers to javafx.fxml;

}