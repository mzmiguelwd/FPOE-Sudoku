package org.example.fpoesudoku.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.example.fpoesudoku.models.AlertHelper;
import org.example.fpoesudoku.models.Sudoku;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.function.UnaryOperator;

public class SudokuGameController {

    @FXML
    private VBox rootVBox; // VBox reference

    private static final int SIZE = 6;
    private Sudoku sudoku;

    private void addBoard(int[][] tablero) {
        // Remove any existing GridPane to avoid duplicates
        rootVBox.getChildren().removeIf(node -> node instanceof GridPane);

        // Create a new GridPane for the board
        GridPane gridPane = new GridPane();
        gridPane.setHgap(1);
        gridPane.setVgap(1);
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

                int finalRow = row;
                int finalCol = col;

                textField.focusedProperty().addListener((obs, oldVal, newVal) -> {
                    if (newVal) {
                        // Highlight the row, column, and quadrant
                        for (Node node : gridPane.getChildren()) {
                            if (node instanceof TextField tf) {
                                Integer tfRow = GridPane.getRowIndex(tf);
                                Integer tfCol = GridPane.getColumnIndex(tf);
                                if (tfRow == null || tfCol == null) continue;

                                boolean sameRow = tfRow == finalRow;
                                boolean sameCol = tfCol == finalCol;
                                boolean sameQuadrant = (tfRow / 2 == finalRow / 2) && (tfCol / 3 == finalCol / 3);

                                if (sameRow || sameCol || sameQuadrant) {
                                    tf.setStyle(tf.getStyle() + "; -fx-background-color: #ffcccc;");
                                }
                            }
                        }
                    } else {
                        // Restore the style (you might want to store and restore the original style more robustly)
                        for (Node node : gridPane.getChildren()) {
                            if (node instanceof TextField tf) {
                                int tfRow = GridPane.getRowIndex(tf);
                                int tfCol = GridPane.getColumnIndex(tf);

                                boolean isSelectBlock = (tfRow / 2 + tfCol / 3) % 2 == 0;
                                boolean isEditable = tf.isEditable();
                                String background = isEditable
                                        ? (isSelectBlock ? "#f2f2f2" : "white")
                                        : (isSelectBlock ? "#d3d3d3" : "lightgray");

                                tf.setStyle(
                                        "-fx-background-color: " + background + ";" +
                                                " -fx-font-size: 18px;" +
                                                " -fx-alignment: center;" +
                                                " -fx-border-color: black;" +
                                                " -fx-border-width: 1;" +
                                                " -fx-background-insets: 0;" +
                                                " -fx-padding: 0;"
                                );
                            }
                        }
                    }
                });

                gridPane.add(textField, col, row);

                // Extra spacing between 2x3 blocks
                int topMargin = (row % 2 == 0 && row != 0) ? 5 : 0;
                int leftMargin = (col % 3 == 0 && col != 0) ? 5 : 0;
                Insets margin = new Insets(topMargin, 0, 0, leftMargin);
                GridPane.setMargin(textField, margin);
            }
        }

        // Add the board to the VBox
        rootVBox.getChildren().add(0, gridPane);

        // Click outside gridPane removes focus from TextFields
        rootVBox.getScene().addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
            Node target = event.getPickResult().getIntersectedNode();

            // Traverse up the node hierarchy to check if the click was inside the gridPane
            while (target != null && target != gridPane) {
                target = target.getParent();
            }

            // If the click was outside the gridPane
            if (target == null) {
                rootVBox.requestFocus(); // Remove focus from any focused TextField
            }
        });
    }

    private int[][] generatePartialSudoku(int[][] completeSudoku) {
        int[][] partialSudoku = new int[6][6];

        // Copy the complete Sudoku into the new partial board
        for (int i = 0; i < 6; i++) {
            System.arraycopy(completeSudoku[i], 0, partialSudoku[i], 0, 6);
        }

        Random random = new Random();

        // Remove exactly 4 cells per 2x3 quadrant
        for (int startRow = 0; startRow < 6; startRow += 2) {
            for (int startCol = 0; startCol < 6; startCol += 3) {
                // Collect all cell positions in the current 2x3 quadrant
                List<int[]> positions = new ArrayList<>();
                for (int row = startRow; row < startRow + 2; row++) {
                    for (int col = startCol; col < startCol + 3; col++) {
                        positions.add(new int[]{row, col});
                    }
                }

                // Shuffle and remove 4 random cells
                Collections.shuffle(positions, random);
                for (int i = 0; i < 4; i++) {
                    int[] pos = positions.get(i);
                    partialSudoku[pos[0]][pos[1]] = 0;
                }
            }
        }

        return partialSudoku;
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
