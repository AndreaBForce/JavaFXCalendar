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
    private Stage alertStage;

    public AlertClose(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;

        alert = new Alert(Alert.AlertType.WARNING, resourceBundle.getString("eventAlertClose.testo"));
        // Necessari per mostrare alert davanti al modal
        alert.initModality(Modality.APPLICATION_MODAL);
        //alert.initOwner(modalStage);

        alertStage = new Stage();
        alertStage.setTitle(resourceBundle.getString("alertStage.testo"));
        alertStage.setAlwaysOnTop(true);
        alertStage.initModality(Modality.APPLICATION_MODAL);

        BorderPane border = new BorderPane();
        Label text = new Label();
        text.setText(alert.getContentText());
        text.setFont(new Font("Arial", 15));
        text.setAlignment(Pos.CENTER);
        border.setTop(text);
        Scene scena = new Scene(border,400,100);
        alertStage.setScene(scena);
    }

    public Stage getAlert() {
        return alertStage;
    }
}
