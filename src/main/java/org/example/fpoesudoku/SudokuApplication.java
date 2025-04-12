package org.example.fpoesudoku;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;

/**

 * Main application class for the Sudoku game.
 * This class handles the initial setup and launch of the user interface.
 * It loads the welcome menu and sets an icon for the application window.
 *  @author Brandon Lasprilla Aristizabal
 *  @author Juan Miguel Manjarrez Zuluaga
 */
public class SudokuApplication extends Application {

    /**
     * Starts the JavaFX application.
     * Loads the welcome screen from FXML, sets the icon, window title, and other properties.
     *
     * @param stage the primary stage for this application
     * @throws IOException if the FXML file cannot be loaded
     */
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

    /**
     * Launches the application.
     *
     * @param args the command-line arguments (not-used)
     */
    public static void main(String[] args) {
        launch();
    }

}