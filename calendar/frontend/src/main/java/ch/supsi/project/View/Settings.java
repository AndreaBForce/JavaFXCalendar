package ch.supsi.project.View;

import ch.supsi.project.servicelayer.PreferencesService;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ResourceBundle;

public class Settings {
    private ResourceBundle resourceBundle;
    private PreferencesService preferencesService;
    private Stage stage;

    public Settings(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
        preferencesService = new PreferencesService();

        stage = new Stage();
        stage.setTitle(resourceBundle.getString("menuPref.testo"));
        stage.setAlwaysOnTop(true);
        stage.initModality(Modality.APPLICATION_MODAL);

        BorderPane settingsBorder = new BorderPane();

    }

    public Stage getStage() {
        return stage;
    }

    public boolean isExist(){
        return preferencesService.isExist();
    }
}
