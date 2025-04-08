package org.example.fpoesudoku.views;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class SudokuApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Cargar la interfaz del menú principal
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/fpoesudoku/sudokuGameMenu.fxml"));
        Parent root = loader.load();
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/org/example/fpoesudoku/images/appIconV1.jpg"))); //LOADS THE APP ICON

        // CREATES THE SCENE WITH THE MENU
        Scene scene = new Scene(root);

        // SET UP FOR THE MAIN SCENE (WINDOW)
        stage.setTitle("Sudoku Game");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}