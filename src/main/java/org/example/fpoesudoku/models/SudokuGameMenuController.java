package org.example.fpoesudoku.models;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

//MENU CONTROLLER

public class SudokuGameMenuController {

    @FXML
    void StartButtonOnActtion(ActionEvent event) {
        try {
            // LOADS THE INTERFACE
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/fpoesudoku/sudokuGame.fxml"));
            Parent root = loader.load();

            // GETS THE CURRENT WINDOW
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

            // CHANGES TO THE GAME SCENE
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace(); // ERROR MANAGMENT
        }
    }
}
