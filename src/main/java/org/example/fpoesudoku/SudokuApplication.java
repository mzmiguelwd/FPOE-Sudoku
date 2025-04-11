package org.example.fpoesudoku;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * se encarga de la vista
 * muestra el menu, define el icono para la aplicacion
 */
public class SudokuApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        // Load the main menu interface from the FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/fpoesudoku/sudoku-welcome-view.fxml"));
        Parent root = loader.load();

        // Set the application icon (from resources)
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/org/example/fpoesudoku/images/app-icon-v1.jpg")));

        // Create the main scene with the loaded layout
        Scene scene = new Scene(root);

        // Set up the main application window
        stage.setTitle("Sudoku Game");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}