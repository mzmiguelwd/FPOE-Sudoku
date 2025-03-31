package org.example.fpoesudoku;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.example.fpoesudoku.models.Sudoku;

import java.util.Random;

// gameController

public class SudokuController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }


    @FXML
    private VBox rootVBox; // VBox reference
    @FXML
    private Button startButton; // Start button reference
    @FXML
    private Button restartButton; // Restart button reference

    private static final int SIZE = 6;
    private Sudoku sudoku;



    private void agregarTablero(int[][] tablero) {
        rootVBox.getChildren().removeIf(node -> node instanceof GridPane); //removes the grid pane to avoid duplicates
        GridPane gridPane = new GridPane();//generates a new Grid pane
        gridPane.setHgap(5);
        gridPane.setVgap(5);
        gridPane.setAlignment(Pos.CENTER); // centers the grid pane
        gridPane.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));

        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 6; col++) {
                TextField textField = new TextField();
                textField.setPrefSize(40, 40);
                textField.setAlignment(javafx.geometry.Pos.CENTER);
                textField.setStyle("-fx-font-size: 18px;"); //generates the field texts

                if (tablero[row][col] != 0) {
                    textField.setText(String.valueOf(tablero[row][col]));
                    textField.setEditable(false);
                    textField.setStyle("-fx-background-color: lightgray; -fx-font-size: 18px; -fx-alignment: center;");
                } //Makes the default sudoku numbers non editable

                gridPane.add(textField, col, row); //Adds the text fields into the grid pane (by columns and rows)
            }
        }



        rootVBox.getChildren().add(0, gridPane); //adds the grid pane to the vBox
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

    } // THE FUNCTION THAT GIVES THE HELP TO COMPLETE PART OF THE SUDOKU

    @FXML
    void onActionMouseClickedQuestionMark(MouseEvent event) {

    }//FUNCTION FOR THE QUESTION MARK (WHAT HAPPENS WHEN IT'S CLICKED)


    @FXML
    void onActionRestartButton(ActionEvent event) {

    } //FUNCTION FOR THE RESTART BUTTON (WHAT HAPPENS WHEN IT'S CLICKED)

    @FXML
    void onActionStartButton(ActionEvent event) {
        if (sudoku == null) { // Generates a new sudoku only if there's none
            sudoku = new Sudoku();
            sudoku.solveSudoku(); // Generates a valid sudoku (a solved one)
        }

        int[][] sudokuParcial = generarSudokuParcial(sudoku.getSudoku()); // hides some values fot the partial sudoku
        agregarTablero(sudokuParcial); // shows the partial sudoku on the interface
    } //FUNCTION FOR THE START BUTTON (WHAT HAPPENS WHE IT'S CLICKED)
}
