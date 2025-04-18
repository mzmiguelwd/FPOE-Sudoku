package org.example.fpoesudoku.models;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Represents a 6x6 Sudoku puzzle and provides methods to solve and validate it.
 *  @author Brandon Lasprilla Aristizabal
 *  @author Juan Miguel Manjarrez Zuluaga
 */
public class Sudoku {

    private int sudoku [][];

    /**
     * Initializes an empty 6x6 Sudoku grid with all cells set to 0.
     */
    public Sudoku() {
        int sudo[][] = {
                {0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0}
        };
        sudoku = sudo;
    }

    /**
     * Solves the current Sudoku using backtracking.
     *
     * @return {@code true} if a solution is found, {@code false} otherwise.
     */
    public boolean solveSudoku() {
        for (int row = 0; row < 6; row++) { // Iterate through each row
            for (int col = 0; col < 6; col++) { // Iterate through each column
                if (sudoku[row][col] == 0) { // Check for an empty cell (value 0)
                    List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);
                    Collections.shuffle(numbers);

                    for (int value : numbers) { // Try random numbers from 1 to 6
                        if (validMovement(row, col, value)) { // Check if the number is valid in this position
                            sudoku[row][col] = value; // Assign a tentative number

                            if (solveSudoku()) { // Recursive call to continue solving
                                return true; // Solution found
                            }

                            // Backtraking: Reset the cell if no solution is found
                            sudoku[row][col] = 0;
                        }
                    }
                    return false; // No valid number worked, backtrack
                }
            }
        }
        return true; // All cells are filled (solution complete)
    }

    /**
     * Solves and prints the Sudoku grid to the console.
     */
    public void showSudoku() {
        solveSudoku(); // Solve the Sudoku before displaying it

        for (int row = 0; row < 6; row++) { // Iterate through each row of the Sudoku
            for (int col = 0; col < 6; col++) { // Iterate through each column of the current row
                System.out.print(sudoku[row][col] + " "); // Print the value at the current position
            }
            System.out.println(); // Move to the next line after printing a row
        }
    }

    /**
     * Checks whether placing a value at a given position is valid.
     *
     * @param row    The row index.
     * @param column The column index.
     * @param value  The value to place.
     * @return {@code true} if the move is valid, {@code false} otherwise.
     */
    private boolean validMovement(int row, int column, int value) {
        // Check if the value is valid in the row, column, and sub-quadrant
        return validateRow(row, value) &&
            validateColumn(column, value) &&
            validateQuadrant(row, column, value);
    }

    /**
     * Validates whether a value can be placed in a specific row.
     *
     * @param row   The row index.
     * @param value The value to check.
     * @return {@code true} if the value does not exist in the row.
     */
    public boolean validateRow(int row, int value) {
        // Validate the existence of the value in the row
        for (int i = 0; i < 6; i++) { // Iterate through all 6 columns in the given row
            if (sudoku[row][i] == value) { // Check if the value already exists in the row
                return false; // Value is not valid in this row
            }
        }
        return true; // Value can be placed in this row
    }

    /**
     * Validates whether a value can be placed in a specific column.
     *
     * @param column The column index.
     * @param value  The value to check.
     * @return {@code true} if the value does not exist in the column.
     */
    public boolean validateColumn(int column, int value) {
        // Validate the existence of the value in the column
        for (int i = 0; i < 6; i++) { // Iterate through all 6 rows in the given column
            if (sudoku[i][column] == value) { // Check if the value exists in the column
                return false; // Value is not valid in this column
            }
        }
        return true; // Value can be placed in this column
    }

    /**
     * Validates whether a value can be placed in the corresponding 2x3 sub-quadrant.
     *
     * @param row    The row index.
     * @param column The column index.
     * @param value  The value to check.
     * @return {@code true} if the value does not exist in the quadrant.
     */
    public boolean validateQuadrant(int row, int column, int value) {
        // Get the current quadrant
        int subQuadrant = currentQuadrant(row, column);

        // Calculate the starting coordinates of the sub-quadrant
        int rowInit = ((subQuadrant - 1) / 2) * 2; // 0, 2 o 4
        int columnInit = ((subQuadrant - 1) % 2) * 3; // 0 o 3

        // Validate the existence of the value in the sub-quadrant
        for (int r = rowInit; r < rowInit + 2; r++) { // Iterate through the 2 rows of the sub-quadrant
            for (int c = columnInit; c < columnInit + 3; c++) { // Iterate through the 3 columns of the sub-quadrant
                if (sudoku[r][c] == value) { // The value already exists in the sub-quadrant
                    return false;
                }
            }
        }
        return true; // The value is valid in this sub-quadrant
    }

    /**
     * Determines the sub-quadrant (1 to 6) of a given cell.
     *
     * @param row    The row index.
     * @param column The column index.
     * @return The quadrant number (1 to 6).
     */
    public static int currentQuadrant(int row, int column) {
        // Determine the row of the sub-quadrant (0, 1 o 2)
        int currentQuadrantRow = row / 2;

        // Determine the column of the sub-qudrant (0 o 1)
        int currentQuadrantcolumn = column / 3;

        // Calculate the sub-quadrant number (1-6)
        return (currentQuadrantRow * 2) + currentQuadrantcolumn + 1;
    }

    /**
     * Returns the current Sudoku grid.
     *
     * @return A 2D array representing the Sudoku grid.
     */
    public int [][] getSudoku() { return sudoku; }

    /**
     * Sets a custom Sudoky grid.
     *
     * @param sudoku A 2D array to replace the current grid.
     */
    public void setSudoku(int[][] sudoku) {
        this.sudoku = sudoku;
    }

}