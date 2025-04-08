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
    private VBox rootVBox; // VBox reference

    private static final int SIZE = 6;
    private Sudoku sudoku;

    private void agregarTablero(int[][] tablero) {
        // Eliminar cualquier GridPane existente para evitar duplicados
        rootVBox.getChildren().removeIf(node -> node instanceof GridPane);

        // Crea un nuevo GridPane para el tablero
        GridPane gridPane = new GridPane();
        gridPane.setHgap(5);
        gridPane.setVgap(5);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));

        // Recorre cada celda de la matriz para crear los TextFields
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

                    // solo permite números del 1 al 6
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

        // Agrega el tablero en la parte superior del VBox
        rootVBox.getChildren().add(0, gridPane);

    }

    private int[][] generarSudokuParcial(int[][] sudokuCompleto) {
        int[][] sudokuParcial = new int[6][6];

        // Copies the solved sudoku
        for (int i = 0; i < 6; i++) {
            System.arraycopy(sudokuCompleto[i], 0, sudokuParcial[i], 0, 6);
        }

        Random random = new Random();
        int celdasAEliminar = 12; // Number of empty cells (empty cells we want per sudoku)

        while (celdasAEliminar > 0) {
            int row = random.nextInt(6);
            int col = random.nextInt(6);

            if (sudokuParcial[row][col] != 0) {
                sudokuParcial[row][col] = 0; // Deletes the number
                celdasAEliminar--;
            }
        }

        return sudokuParcial;
    }

    @FXML
    void onActionMouseClickedLightBulb(MouseEvent event) {

    } // Function to handles the light bulb: help to complete part of the sudoku

    @FXML
    void onActionMouseClickedQuestionMark(MouseEvent event) {

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
            int[][] sudokuParcial = generarSudokuParcial(sudoku.getSudoku());

            // Display the partially completed Sudoku board on the UI
            agregarTablero(sudokuParcial);

            System.out.println("Initialization started");
        } else {
            System.out.println("Initialization cancelled");
        }
    }

    @FXML
    void onActionSubmitButton(ActionEvent event) {

    }

}
