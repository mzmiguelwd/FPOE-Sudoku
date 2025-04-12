package org.example.fpoesudoku.views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.fpoesudoku.SudokuApplication;
import java.io.IOException;

/**
 * Represents the welcome window of the Sudoku game.
 * This class extends {@link Stage} and loads the welcome screen from the corresponding FXML file.
 * It uses the singleton pattern to ensure only one instance of the welcome view is created.
 *  @author Brandon Lasprilla Aristizabal
 *  @author Juan Miguel Manjarrez Zuluaga
 */
public class SudokuWelcomeView extends Stage {

    /**
     * Constructs the welcome view stage.
     * Loads the "sudoku-welcome-view.fxml" layout and sets up the window's title, size, and properties.
     *
     * @throws IOException if the FXML file cannot be loaded
     */
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

    /**
     * Returns the single instance of {@code SudokuWelcomeView}.
     * If it does not exist yet, a new instance is created.
     *
     * @return the singleton instance of the welcome view
     * @throws IOException if the instance cannot be created due to FXML loading issues
     */
    public static SudokuWelcomeView getInstance() throws IOException {
        if (SudokuWelcomeViewHolder.INSTANCE == null) {
            SudokuWelcomeViewHolder.INSTANCE = new SudokuWelcomeView();
            return SudokuWelcomeViewHolder.INSTANCE;
        } else {
            return SudokuWelcomeViewHolder.INSTANCE;
        }
    }

    /**
     * Holder class for lazy-loaded singleton instance.
     */
    private static class SudokuWelcomeViewHolder {
        private static SudokuWelcomeView INSTANCE;
    }

}