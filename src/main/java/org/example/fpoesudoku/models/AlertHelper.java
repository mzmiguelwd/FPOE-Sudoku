package org.example.fpoesudoku.models;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.Region;
import java.util.Optional;

/**
 * Esta clase se encarga de manejar todas las alertas
 */
public class AlertHelper {

    // Method to show a confirmation alert and returns true if the user clicks OK
    // Used for the "Empezar" button message confirmation

    /**
     *metodo encargado de mostrar alertas de confirmacion
     */
    public static boolean showConfirmationAlert(String title, String message) {
        /**
         * @param title
         * @param message
         */

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

    // Method to show an information alert

    /**
     *Metodo encargado de mostrar alertas de informacion
     */
    public static void showInfoAlert(String title,String header, String message) {
        /**
         * @param title
         * @param header
         * @param message
         */
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setMinHeight(Region.USE_PREF_SIZE);
        dialogPane.setMinWidth(520);

        alert.showAndWait();
    }

    // Method to show an error alert

    /**
     *
     *Metodo encargado de mostrar alertas de error
     */
    public static void showErrorAlert(String title,String header, String message) {
        /**
         * @param title
         * @param header
         * @param message
         */
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Method to show a warning alert

    /**
     *
     *Metodo encargado de mostrar alertas de advertencia
     */
    public static void showWarningAlert(String title, String message) {
        /**
         * @param title
         * @param message
         */
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
