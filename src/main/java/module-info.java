module org.example.fpoesudoku {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.fpoesudoku to javafx.fxml;
    exports org.example.fpoesudoku;
}