package ch.supsi.project.View;

import javafx.scene.control.Alert;
import javafx.stage.Modality;

import java.util.ResourceBundle;

public class AlertEmpty {
    private ResourceBundle resourceBundle;
    private Alert alertEmpty;

    public AlertEmpty(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;

        alertEmpty = new Alert(Alert.AlertType.WARNING, resourceBundle.getString("eventAlert.testo"));
        alertEmpty.initModality(Modality.APPLICATION_MODAL);

    }

    public Alert getAlertEmpty() {
        return alertEmpty;
    }
}
