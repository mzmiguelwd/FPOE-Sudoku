package org.example.fpoesudoku.models;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.Region;
import java.util.Optional;

/**
 * Utility class responsible for displaying different types of alerts
 * (confirmation, information, error, and warning) in the user interface.
 *  @author Brandon Lasprilla Aristizabal
 *  @author Juan Miguel Manjarrez Zuluaga
 */
public class AlertHelper {

    /**
     * Displays a confirmation alert with "Iniciar" and "Cancelar" buttons.
     *
     * @param title   The title of the alert.
     * @param message The message displayed in the alert body.
     * return {@code true} if the user clicks "Iniciar", {@code false} otherwise.
     */
    public static boolean showConfirmationAlert(String title, String message) {
        // Custom buttons
        ButtonType yesButton = new ButtonType("Iniciar");
        ButtonType noButton = new ButtonType("Cancelar");

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, message, yesButton, noButton);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == yesButton;
    }

    /**
     * Displays an informational alert.
     *
     * @param title   The title of the alert.
     * @param header  The header text of the alert.
     * @param message The message displayed in the alert body.
     */
    public static void showInfoAlert(String title,String header, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setMinHeight(Region.USE_PREF_SIZE);
        dialogPane.setMinWidth(520);

        alert.showAndWait();
    }

    /**
     * Displays an error alert.
     *
     * @param title   The title of the alert.
     * @param header  The header text of the alert.
     * @param message The message displayed in the alert body.
     */
    public static void showErrorAlert(String title,String header, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Displays a warning alert.
     *
     * @param title   The title of the alert.
     * @param message The message displayed in the alert body.
     */
    public static void showWarningAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}