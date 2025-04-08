package org.example.fpoesudoku.views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.fpoesudoku.SudokuApplication;
import java.io.IOException;

public class SudokuWelcomeView extends Stage {

    public SudokuWelcomeView() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                SudokuApplication.class.getResource("sudoku-welcome-view.fxml")
        );
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 320, 240);
        this.setTitle("Sudoku Welcome!");
        this.setScene(scene);
        this.setResizable(false);
    }

    public static SudokuWelcomeView getInstance() throws IOException {
        if (SudokuWelcomeViewHolder.INSTANCE == null) {
            SudokuWelcomeViewHolder.INSTANCE = new SudokuWelcomeView();
            return SudokuWelcomeViewHolder.INSTANCE;
        } else {
            return SudokuWelcomeViewHolder.INSTANCE;
        }
    }

    private static class SudokuWelcomeViewHolder {
        private static SudokuWelcomeView INSTANCE;
    }

}