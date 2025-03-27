package org.example.fpoesudoku;

import javafx.application.Application;
import javafx.stage.Stage;
import org.example.fpoesudoku.models.Sudoku;

import java.io.IOException;

public class SudokuApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Sudoku sudoku = new Sudoku();
        sudoku.showSudoku();
    }

    public static void main(String[] args) {
        launch();
    }
}