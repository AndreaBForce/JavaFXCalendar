package ch.supsi.project.View;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ResourceBundle;

public class AlertClose {
    private ResourceBundle resourceBundle;
    private Alert alert;

    public AlertClose(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;

        alert = new Alert(Alert.AlertType.WARNING, resourceBundle.getString("eventAlertClose.testo"));
        // Necessari per mostrare alert davanti al modal
        alert.initModality(Modality.APPLICATION_MODAL);

        alert.show();
    }

    public Alert getAlert() {
        return alert;
    }
}
