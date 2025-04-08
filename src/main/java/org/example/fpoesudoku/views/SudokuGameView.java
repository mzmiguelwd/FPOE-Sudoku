package org.example.fpoesudoku.views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.fpoesudoku.SudokuApplication;
import org.example.fpoesudoku.controllers.SudokuGameController;
import java.io.IOException;

public class SudokuGameView extends Stage {

    private SudokuGameController controller;

    public SudokuGameView() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                SudokuApplication.class.getResource("sudoku-game-view.fxml")
        );
        Scene scene = new Scene(fxmlLoader.load());
        this.controller = fxmlLoader.getController();
        this.setTitle("Sudoku Game");
        this.setScene(scene);
        this.setResizable(false);
    }

    public SudokuGameController getController() { return controller; }

    public static SudokuGameView getInstance() throws IOException {
        if (SudokuGameViewHolder.INSTANCE == null) {
            SudokuGameViewHolder.INSTANCE = new SudokuGameView();
            return SudokuGameViewHolder.INSTANCE;
        } else {
            return SudokuGameViewHolder.INSTANCE;
        }
    }

    private static class SudokuGameViewHolder {
        private static SudokuGameView INSTANCE;
    }

}