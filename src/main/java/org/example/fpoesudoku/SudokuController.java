package org.example.fpoesudoku;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import javafx.scene.input.MouseEvent;

// gameController

public class SudokuController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
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

    } //FUNCTION FOR THE START BUTTON (WHAT HAPPENS WHE IT'S CLICKED)
}
