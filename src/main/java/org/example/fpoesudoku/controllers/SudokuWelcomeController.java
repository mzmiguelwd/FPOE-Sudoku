package org.example.fpoesudoku.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.fpoesudoku.models.AlertHelper;
import java.io.IOException;

/**
 * Controller class for the welcome menu of the Sudoku game.
 * Displays an introductory description and transitions to the main game interface.
 */
public class SudokuWelcomeController {

    /**
     * Handles the "Empezar" button action.
     * Shows a description of the mini-project and loads the main Sudoku game interface.
     *
     * @param event the action event triggered by clicking the "Empezar" button.
     */
    @FXML
    void onActionStartButton(ActionEvent event) {
        String description = """
                En este miniproyecto jugarás una versión del Sudoku en una cuadrícula de 6x6.
                El objetivo es completar la cuadrícula con números del 1 al 6, asegurando que
                cada fila, columna y bloque de 2x3 contenga todos los números sin repetir.
                
                El juego incluye entrada de números con el mouse y el teclado, un sistema de
                ayudas limitadas, y validación automática de los números ingresados para
                cumplir con las reglas.
                """;

        AlertHelper.showInfoAlert("Sudoku - Descripción",null, description);

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