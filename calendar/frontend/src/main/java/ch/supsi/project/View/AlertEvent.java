package ch.supsi.project.View;

import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ResourceBundle;

public class AlertEvent {
    private ResourceBundle resourceBundle;
    private Stage stage;

    public AlertEvent(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;

        stage = new Stage();
        stage.setTitle(resourceBundle.getString("alertStage.testo"));
        stage.setAlwaysOnTop(true);
        stage.initModality(Modality.APPLICATION_MODAL);
    }

    public Stage getStage() {
        return stage;
    }
}
