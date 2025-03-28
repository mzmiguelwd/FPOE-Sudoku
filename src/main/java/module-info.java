module org.example.fpoesudoku {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens org.example.fpoesudoku to javafx.fxml;
    exports org.example.fpoesudoku;
    opens org.example.fpoesudoku.models to javafx.fxml;

}