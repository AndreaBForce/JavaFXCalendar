package ch.supsi.project.View;

import ch.supsi.project.servicelayer.PreferencesService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.util.Locale;
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
        //TODO ENG IT
        //TODO SOCIO SIIOCI CSV
        //PATH FILE?
        final ObservableList<String> lingue = FXCollections.observableArrayList();
        lingue.add("ENG");
        lingue.add("IT");
        ComboBox<String> lingua = new ComboBox<>(lingue);

        final ObservableList<String> fileExt = FXCollections.observableArrayList();
        fileExt.add("CSV");
        fileExt.add("JSON");
        ComboBox<String> fileType = new ComboBox<>(fileExt);

        VBox settings = new VBox();
        HBox casella1 = new HBox();
        HBox casella2 = new HBox();
        HBox casellaDir = new HBox();
        HBox casellaSave = new HBox();

        Label lin = new Label();
        Label filesal = new Label();
        TextField textFieldPercoso = new TextField();

        lin.setText(resourceBundle.getString("labelLingua.testo")+"  ");
        filesal.setText(resourceBundle.getString("labelType.testo")+": ");

        casella1.getChildren().addAll(lin,lingua);
        casella2.getChildren().addAll(filesal,fileType);

        casella1.setSpacing(20);
        casella2.setSpacing(20);
        settings.setSpacing(40);

        casella1.setAlignment(Pos.CENTER);
        casella2.setAlignment(Pos.CENTER);

        DirectoryChooser directoryChooser = new DirectoryChooser();

        String dir = System.getProperty("user.home");

        String OpSystem = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);

        directoryChooser.setInitialDirectory(new File(dir));

        Button button = new Button(resourceBundle.getString("buttonDir.testo"));
        button.setOnAction(e -> {
            File selectedDirectory = directoryChooser.showDialog(stage);
            textFieldPercoso.setText(selectedDirectory.getAbsolutePath());
        });


        Button buttonSalva = new Button(resourceBundle.getString("buttonS.testo"));

        buttonSalva.setOnAction(e -> {

        });

        casellaDir.getChildren().addAll(textFieldPercoso,button);
        casellaSave.getChildren().addAll(buttonSalva);

        casellaDir.setAlignment(Pos.TOP_CENTER);
        casellaSave.setAlignment(Pos.CENTER);

        settings.getChildren().addAll(casella1,casella2,casellaDir,casellaSave);



        settingsBorder.setCenter(settings);

        Scene setting = new Scene(settingsBorder, 300, 300);

        stage.setScene(setting);
    }

    public Stage getStage() {
        return stage;
    }

    public boolean isExist(){
        return preferencesService.isExist();
    }
}
