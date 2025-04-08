package org.example.fpoesudoku.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class SudokuWelcomeController {

    // Method triggered when the "Iniciar" button is clicked
    @FXML
    void onActionStartButton(ActionEvent event) {
        try {
            // Load the game interface from the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/fpoesudoku/sudoku-game-view.fxml"));
            Parent root = loader.load();

            // Get the current window (stage) from the event source
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

            // Set the scene to the game interface and show it
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            // Prints any exception that occurs during loading
            e.printStackTrace();
        }
    }

}
