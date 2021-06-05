package ch.supsi.project;

import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ResourceBundle;

public class AlertView {
    private ResourceBundle resourceBundle;
    private Alert alert;
    private Stage alertStage;

    public AlertView(ResourceBundle resourceBundle, Stage modalStage) {
        this.resourceBundle = resourceBundle;

        alert = new Alert(Alert.AlertType.WARNING, resourceBundle.getString("eventAlert.testo"));
        // Necessari per mostrare alert davanti al modal
        alert.initModality(Modality.APPLICATION_MODAL);
        //alert.initOwner(modalStage);

        alertStage = new Stage();
        alertStage.setTitle(resourceBundle.getString("alertStage.testo"));
        alertStage.setAlwaysOnTop(true);
        alertStage.initModality(Modality.APPLICATION_MODAL);
    }

    public Stage getAlert() {
        return alertStage;
    }
}
