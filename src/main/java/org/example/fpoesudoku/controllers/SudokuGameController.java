package org.example.fpoesudoku.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.example.fpoesudoku.models.AlertHelper;
import org.example.fpoesudoku.models.Sudoku;
import java.util.Random;
import java.util.function.UnaryOperator;

public class SudokuGameController {
    @FXML
    AlertHelper alertHelper=new AlertHelper();
    @FXML
    private VBox rootVBox; // VBox reference

    private static final int SIZE = 6;
    private Sudoku sudoku;

    private void addBoard(int[][] tablero) {
        // Remove any existing GridPane to avoid duplicates
        rootVBox.getChildren().removeIf(node -> node instanceof GridPane);

        // Create a new GridPane for the board
        GridPane gridPane = new GridPane();
        gridPane.setHgap(5);
        gridPane.setVgap(5);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));

        // Loop through each cell of the matrix to create TextFields
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 6; col++) {
                TextField textField = new TextField();
                textField.setPrefSize(40, 40);
                textField.setAlignment(javafx.geometry.Pos.CENTER);
                textField.setStyle("-fx-font-size: 18px;");

                boolean isShadedBlock = (row / 2 + col / 3) % 2 == 0;
                int value = tablero[row][col];

                if (value != 0) {
                    textField.setText(String.valueOf(value));
                    textField.setEditable(false);
                    textField.setPrefSize(40, 40);
                    textField.setMinSize(40, 40);
                    textField.setMaxSize(40, 40);
                    textField.setStyle(
                            (isShadedBlock ? "-fx-background-color: #d3d3d3;" : "-fx-background-color: lightgray;") +
                                    " -fx-font-size: 18px;" +
                                    " -fx-alignment: center;" +
                                    " -fx-border-color: black;" +
                                    " -fx-border-width: 1;" +
                                    " -fx-background-insets: 0;" +
                                    " -fx-padding: 0;"
                    );
                } else {
                    textField.setEditable(true);
                    textField.setPrefSize(40, 40);
                    textField.setMinSize(40, 40);
                    textField.setMaxSize(40, 40);

                    String background = isShadedBlock ? "#f2f2f2" : "white";
                    textField.setStyle(
                            "-fx-background-color: " + background + ";" +
                                    " -fx-font-size: 18px;" +
                                    " -fx-alignment: center;" +
                                    " -fx-border-color: black;" +
                                    " -fx-border-width: 1;" +
                                    " -fx-background-insets: 0;" +
                                    " -fx-padding: 0;"
                    );

                    // Only allow numbers from 1 to 6
                    UnaryOperator<TextFormatter.Change> filter = change -> {
                        String newText = change.getControlNewText();
                        if (newText.matches("[1-6]?")) {
                            return change;
                        }
                        return null;
                    };
                    textField.setTextFormatter(new TextFormatter<>(filter));
                }

                gridPane.add(textField, col, row);
            }
        }

        // Add the board to the VBox
        rootVBox.getChildren().add(0, gridPane);
    }

    private int[][] generatePartialSudoku(int[][] completeSudoku) {
        int[][] partialSudoku = new int[6][6];

        // Copy the complete Sudoku into the new partial board
        for (int i = 0; i < 6; i++) {
            System.arraycopy(completeSudoku[i], 0, partialSudoku[i], 0, 6);
        }

        Random random = new Random();
        int cellsToRemove = 12; // Number of cells to empty

        // Randomly remove numbers from the board until the desired amount is reached
        while (cellsToRemove > 0) {
            int row = random.nextInt(6);
            int col = random.nextInt(6);

            if (partialSudoku[row][col] != 0) {
                partialSudoku[row][col] = 0; // Empty the cell
                cellsToRemove--;
            }
        }

        return partialSudoku;
    }

    @FXML
    void onActionMouseClickedLightBulb(MouseEvent event) {

    } // Function to handles the light bulb: help to complete part of the sudoku

    @FXML
    void onActionMouseClickedQuestionMark(MouseEvent event) {
    alertHelper.showInfoAlert("Instrucciones del juego","instrucciones del sudoku 6x6","1. El tablero está compuesto por 6 filas y 6 columnas, formando un total de 36 casillas.\n" +
            "\n" +
            "2. El tablero se divide en 6 regiones de igual tamaño, de 3x2 (3 columnas por 2 filas) o 2x3, según el diseño.\n" +
            "\n" +
            "3. Debes llenar todas las casillas con números del 1 al 6, siguiendo estas reglas:\n" +
            "\n" +
            "4. Cada fila debe contener los números del 1 al 6 sin repetir.\n" +
            "\n" +
            "5. Cada columna debe contener los números del 1 al 6 sin repetir.\n" +
            "\n" +
            "6. Cada región debe contener los números del 1 al 6 sin repetir.\n" +
            "\n" +
            "7. No hay soluciones múltiples: cada tablero tiene una única solución correcta");
    } // Function to handles the question mark

    @FXML
    void onActionRestartButton(ActionEvent event) {

    } // Function to handles the Restart button click

    @FXML
    void onActionStartGameButton(ActionEvent event) {
        // Ask the user to confirm if they want to start the game
        boolean confirm = AlertHelper.showConfirmationAlert("Confirmación", "¿Seguro que quieres iniciar el juego?");

        if (confirm) {
            // Create a new Sudoku instance
            sudoku = new Sudoku();

            // Generate a valid, fully solved Sudoku board
            sudoku.solveSudoku();

            // Generate a partially hidden Sudoku based on the solved board
            int[][] sudokuParcial = generatePartialSudoku(sudoku.getSudoku());

            // Display the partially completed Sudoku board on the UI
            addBoard(sudokuParcial);

            System.out.println("Initialization started");
        } else {
            System.out.println("Initialization cancelled");
        }
    }

    @FXML
    void onActionSubmitButton(ActionEvent event) {

    }

}
