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
import java.util.*;
import java.util.function.UnaryOperator;

/**
 * Main controller for the 6x6 Sudoku game.
 * Manages the interaction logic between the view and the game model.
 */
public class SudokuGameController {

    @FXML
    AlertHelper alertHelper = new AlertHelper();

    @FXML
    private VBox rootVBox; // Main container for the Sudoku board

    private static final int SIZE = 6;
    private Sudoku sudoku;
    private GridPane boardGrid;

    @FXML
    int[][] sudokuPartial; // Copy of the current Sudoku board with some cells removed
    int[][] sudokuInitial; // Copy of the initial state of the Sudoku board (for resets)

    /**
     * Prints the Sudoku board to the console (for debugging purposes).
     *
     * @param board the Sudoku board to print
     */
    public void printSudoku(int[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    /**
     * Dynamically adds the Sudoku board to the view as a grid of TextFields.
     * Removes any previous board and creates a new one with styles and input validation.
     *
     * @param board the matrix representing the board to display
     */
    private void addBoard(int[][] board) {
        // Remove any existing GridPane to avoid duplicates
        rootVBox.getChildren().removeIf(node -> node instanceof GridPane);

        // Create a new GridPane for the board
        GridPane gridPane = createGridPane();

        // Loop through each cell of the matrix to create TextFields
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 6; col++) {
                TextField cell = createCell(board[row][col], row, col);
                addHighlightingBehavior(cell, row, col, gridPane);
                gridPane.add(cell, col, row);
                GridPane.setMargin(cell, getCellMargin(row, col));
            }
        }

        // Add the gridPane to the UI and keep reference for future use
        rootVBox.getChildren().add(0, gridPane);
        boardGrid = gridPane;

        // Add global handler to remove focus when clicking outside the board
        addFocusLossHandler(gridPane);
    }

    /**
     * Creates and configures a GridPane layout for the Sudoku board.
     *
     * @return a styled and padded GridPane
     */
    private GridPane createGridPane() {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(1);
        gridPane.setVgap(1);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        return gridPane;
    }

    /**
     * Creates a single TextField cell with appropriate value, styling, and input validation.
     *
     * @param value the numeric value of the cell (0 if editable)
     * @param row   the row index of the cell
     * @param col   the column index of the cell
     * @return a configured TextField instance
     */
    private TextField createCell(int value, int row, int col) {
        TextField textField = new TextField();
        textField.setPrefSize(40, 40);
        textField.setAlignment(Pos.CENTER);

        boolean shaded = (row / 2 + col / 3) % 2 == 0;
        boolean isEditable = value == 0;
        String background = getBackgroundColor(shaded, isEditable);

        textField.setText(value == 0 ? "" : String.valueOf(value));
        textField.setEditable(isEditable);
        textField.setStyle(getCellStyle(background));

        if (isEditable) {
            addInputFilter(textField);
        }

        return textField;
    }

    /**
     * Adds a numeric input filter that allows only digits from 1 to 6.
     *
     * @param textField the TextField to restrict input on
     */
    private void addInputFilter(TextField textField) {
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();
            return newText.matches("[1-6]?") ? change : null;
        };
        textField.setTextFormatter(new TextFormatter<>(filter));
    }

    /**
     * Adds behaviour to highlight related cells (row, column, block) when the cell gains focus.
     *
     * @param cell the TextField to attach the behavior to
     * @param row  the row index of the cell
     * @param col  the column index of the cell
     * @param grid the parent GridPane containing all cells
     */
    private void addHighlightingBehavior(TextField cell, int row, int col, GridPane grid) {
        cell.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (isNowFocused) {
                highlightGroup(grid, row, col);
            } else {
                resetBoardStyle(grid);
            }
        });
    }

    /**
     * Highlights the entire row, column, and 2x3 block for the selected cell.
     *
     * @param grid the GridPane containing the board
     * @param row  the focused row index
     * @param col  the focused column index
     */
    private void highlightGroup(GridPane grid, int row, int col) {
        for (Node node : grid.getChildren()) {
            if (node instanceof TextField tf) {
                Integer r = GridPane.getRowIndex(tf);
                Integer c = GridPane.getColumnIndex(tf);
                if (r == null || c == null) continue;

                boolean sameRow = r == row;
                boolean sameCol = c == col;
                boolean sameBlock = (r / 2 == row / 2) && (c / 3 == col / 3);

                if (sameRow || sameCol || sameBlock) {
                    tf.setStyle(tf.getStyle() + "; -fx-background-color: #ffcccc;");
                }
            }
        }
    }

    /**
     * Resets all cell styles to their original color based on shaded/editable state.
     *
     * @param grid the GridPane whose children will be reset
     */
    private void resetBoardStyle(GridPane grid) {
        for (Node node : grid.getChildren()) {
            if (node instanceof TextField tf) {
                int r = GridPane.getRowIndex(tf);
                int c = GridPane.getColumnIndex(tf);
                boolean shaded = (r / 2 + c / 3) % 2 == 0;
                boolean editable = tf.isEditable();
                String background = getBackgroundColor(shaded, editable);
                tf.setStyle(getCellStyle(background));
            }
        }
    }

    /**
     * Determines the background color for a cell based on its shaded and editable status.
     *
     * @param shaded whether the block should have a shaded background
     * @param editable whether the cell is user-editable
     * @return the appropriate background color as a hex string
     */
    private String getBackgroundColor(boolean shaded, boolean editable) {
        if (editable) return shaded ? "#f2f2f2" : "white";
        return shaded ? "#d3d3d3" : "lightgray";
    }

    /**
     * Return a complete style string for a Sudoku cell.
     *
     * @param backgroundColor the background color to apply
     * @return a concatenated CSS style string
     */
    private String getCellStyle(String backgroundColor) {
        return String.format(
                "-fx-background-color: %s; " +
                "-fx-font-size: 18px; " +
                "-fx-alignment: center; " +
                "-fx-border-color: black; " +
                "-fx-border-width: 1; " +
                "-fx-background-insets: 0; " +
                "-fx-padding: 0;", backgroundColor
        );
    }

    /**
     * Calculates cell margins to add extra spacing between Sudoku blocks.
     *
     * @param row the row index
     * @param col the column index
     * @return Insets with top and left padding for visual separation
     */
    private Insets getCellMargin(int row, int col) {
        int top = (row % 2 == 0 && row != 0) ? 5 : 0;
        int left = (col % 3 == 0 && col != 0) ? 5 : 0;
        return new Insets(top, 0, 0, left);
    }

    /**
     * Adds a global focus-loss handler that clears focus from the board when clicking outside.
     *
     * @param gridPane the Sudoku GridPane to monitor for external clicks
     */
    private void addFocusLossHandler(GridPane gridPane) {
        rootVBox.getScene().addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
            Node target = event.getPickResult().getIntersectedNode();
            while (target != null && target != gridPane) {
                target = target.getParent();
            }
            if (target == null) {
                rootVBox.requestFocus(); // Clear focus from all fields
            }
        });
    }

    /**
     * Generates a partial version of the complete Sudoku board by
     * removing exactly 4 cells per 2x3 region.
     *
     * @param completeSudoku the fully solved Sudoku board.
     * @return a matrix with some cells empty (represented by zeros).
     */
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

        sudokuPartial = partialSudoku;
        printSudoku(sudokuPartial);

        // Copy of the initial state of the Sudoku board that won't be modified by other functions
        sudokuInitial = new int[6][6];
        for (int i = 0; i < 6; i++) {
            System.arraycopy(partialSudoku[i], 0, sudokuInitial[i], 0, 6);
        }
        System.out.println("Copy of the initial Sudoky phase created...");

        return partialSudoku;
    }

    /**
     * Starts a new Sudoku game after user confirmation.
     * It initializes a new Sudoku object, generates a solved board,
     * creates a partially hidden version, and displays it on the screen.
     *
     * @param event button click event on the "Iniciar" button
     */
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
            int[][] sudokuPartial = generatePartialSudoku(sudoku.getSudoku());

            // Display the partially completed Sudoku board on the UI
            addBoard(sudokuPartial);

            System.out.println("Initialization started");
        } else {
            System.out.println("Initialization cancelled");
        }
    }

    /**
     * Resets the current game to its initial state,
     * restoring the board to the version shown at the beginning.
     *
     * @param event button click event on the "Resetear" button.
     */
    @FXML
    void onActionRestartButton(ActionEvent event) {
        // Remove the current board if it exists
        rootVBox.getChildren().removeIf(node -> node instanceof GridPane);

        if (sudoku == null || sudokuPartial == null) {
            alertHelper.showErrorAlert("Error","", "Debes iniciar un juego primero.");
            System.out.println("No existe ningun tablero...");
            return;
        }

        // Create a new copy of the initial board to restart the game
        int[][] newPartial = new int[6][6];
        for (int i = 0; i < 6; i++) {
            System.arraycopy(sudokuInitial[i], 0, newPartial[i], 0, 6);
        }

        sudokuPartial = newPartial;

        // Add the restarted board back to the interface
        addBoard(sudokuPartial);
    }

    /**
     * Validates the current state of the Sudoku board entered by the user.
     * If the board matches the solved version, a success alert is shown.
     * Otherwise, an error alert indicates the puzzle is not yet solved.
     *
     * @param event button click event on the "Validate" button
     */
    @FXML
    void onActionValidateButton(ActionEvent event) {
        // Check if a game has started
        if (sudoku == null || sudokuPartial == null) {
            alertHelper.showErrorAlert("Error","", "Debes iniciar un juego primero.");
            System.out.println("No existe ningun tablero...");
            return;
        }

        // Loop through the nodes in the GridPane to capture user input
        for (Node node : boardGrid.getChildren()) {
            if (node instanceof TextField) {
                Integer row = GridPane.getRowIndex(node);
                Integer col = GridPane.getColumnIndex(node);

                // Fallback in case row/col is null
                if (row == null) row = 0;
                if (col == null) col = 0;

                String text = ((TextField) node).getText().trim();

                if (!text.isEmpty()) {
                    try {
                        int value = Integer.parseInt(text);
                        sudokuPartial[row][col] = value;
                    } catch (NumberFormatException e) {
                        sudokuPartial[row][col] = 0; // Invalid input set to 0
                    }
                } else {
                    sudokuPartial[row][col] = 0; // Empty cell
                }
            }
        }

        int [][] solution = sudoku.getSudoku();

        // Check every cell to see if the user's input matches the solution
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                if (sudokuPartial[i][j] != solution[i][j]) {
                    alertHelper.showErrorAlert("Un momento...", "", "Oops! El Sudoku no ha sido resuelto correctamente aún.");
                    System.out.println("Solución incorrecta en: [" + i + ", " + j + "]");
                    return;
                }
            }
        }

        // If all cells match, the puzzle is solved
        alertHelper.showInfoAlert("Congratulations!", "", "You've successfully completed the Sudoku!");
        System.out.println("Sudoku completed correctly.");
    }

    /**
     * Displays an informational alert with the game instructions.
     *
     * @param event mouse click event on the question mark icon.
     */
    @FXML
    void onActionMouseClickedQuestionMark(MouseEvent event) {
        String title = "Instrucciones del juego";
        String header = "Cómo jugar Sudoku 6x6";
        String content =
                "1. El tablero está compuesto por 6 filas y 6 columnas, formando un total de 36 casillas.\n\n" +
                        "2. Se divide en 6 regiones del mismo tamaño: 2x3 (2 filas x 3 columnas).\n\n" +
                        "3. Debes llenar todas las casillas con números del 1 al 6, siguiendo estas reglas:\n\n" +
                        "4. Cada fila debe contener los números del 1 al 6, sin repetir.\n\n" +
                        "5. Cada columna debe contener los números del 1 al 6, sin repetir.\n\n" +
                        "6. Cada región debe contener los números del 1 al 6, sin repetir.\n\n" +
                        "7. Cada tablero tiene una única solución correcta.";
        alertHelper.showInfoAlert(title, header, content);
    }

    /**
     * Reveals a hint to the player by filling in a random empty cell
     * with its correct value. Only available if more than two cells remain empty.
     *
     * @param event mouse click event on the lightbulb icon
     */
    @FXML
    void onActionMouseClickedLightBulb(MouseEvent event) {
        int minRemainingCells = 2; // Minimum number of empty cells before hints are disabled

        if (sudoku == null || sudokuPartial == null) {
            alertHelper.showErrorAlert("Error","", "Debes iniciar un juego primero.");
            System.out.println("Aún no existe ningún tablero...");
            return;
        }

        // Retrieve the grid pane from the root VBox
        GridPane gridPane = null;
        for (javafx.scene.Node node : rootVBox.getChildren()) {
            if (node instanceof GridPane) {
                gridPane = (GridPane) node;
                break;
            }
        }

        if (gridPane == null) {
            alertHelper.showErrorAlert("Error","", "No se encontró el tablero.");
            return;
        }

        // Collect all currently empty cells
        List<int[]> emptyCells = new ArrayList<>();
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (sudokuPartial[i][j] == 0) {
                    emptyCells.add(new int[]{i, j});
                }
            }
        }

        if (emptyCells.size() > minRemainingCells) {
            // Select a random empty cell
            Random rand = new Random();
            int[] cell = emptyCells.get(rand.nextInt(emptyCells.size()));
            int row = cell[0];
            int col = cell[1];

            // Retrieve the correct value from the original Sudoku board
            int correctValue = sudoku.getSudoku()[row][col];
            sudokuPartial[row][col] = correctValue;

            // Update the corresponding TextField in the UI
            for (javafx.scene.Node node : gridPane.getChildren()) {
                if (GridPane.getRowIndex(node) == row &&
                        GridPane.getColumnIndex(node) == col &&
                        node instanceof TextField) {
                    TextField tf = (TextField) node;
                    tf.setText(String.valueOf(correctValue));
                    tf.setEditable(false);
                    tf.setStyle(tf.getStyle() + "; -fx-text-fill: green;"); // Highlight hint in green
                    break;
                }
            }

            System.out.println("Ayuda aplicada en [" + row + "," + col + "] = " + correctValue);
        } else {
            alertHelper.showWarningAlert("Advertencia","Ya no te quedan ayudas.");
            System.out.println("Ya no te quedan ayudas...");
        }

    }

}