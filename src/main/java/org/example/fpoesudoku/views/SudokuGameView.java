package org.example.fpoesudoku.views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.fpoesudoku.SudokuApplication;
import org.example.fpoesudoku.controllers.SudokuGameController;
import java.io.IOException;

/**
 * This class represents the main game view of the Sudoku application.
 * It loads the UI from an FXML file and initializes the game controller.
 */
public class SudokuGameView extends Stage {

    private SudokuGameController controller;

    /**
     * Creates the Sudoku game view by loading the FXML layout and setting up the stage.
     *
     * @throws IOException if the FXML file can't be loaded.
     */
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

    /**
     * Returns the controller associated with this game view.
     *
     * @return the SudokuGameController instance.
     */
    public SudokuGameController getController() {
        return controller;
    }

    /**
     * Returns a single instance of the SudokuGameView (Singleton).
     * If the view hasn't been created yet, it initializes it.
     *
     * @return the unique SudokuGameView instance.
     * @throws IOException if the view can't be loaded.
     */
    public static SudokuGameView getInstance() throws IOException {
        if (SudokuGameViewHolder.INSTANCE == null) {
            SudokuGameViewHolder.INSTANCE = new SudokuGameView();
            return SudokuGameViewHolder.INSTANCE;
        } else {
            return SudokuGameViewHolder.INSTANCE;
        }
    }

    /**
     * Holder class for the singleton instance of SudokuGameView.
     */
    private static class SudokuGameViewHolder {
        private static SudokuGameView INSTANCE;
    }

}