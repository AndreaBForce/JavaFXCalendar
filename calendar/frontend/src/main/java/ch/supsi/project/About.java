package ch.supsi.project;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Properties;
import java.util.ResourceBundle;

public class About {
    private ResourceBundle resourceBundle;
    private Properties propietaBuild;
    private final String numVersion;
    private final String buildDate;
    private final String appName;
    private Stage aboutStage;
    private Scene scena;

    public About(ResourceBundle resourceBundle){
        this.resourceBundle = resourceBundle;

        propietaBuild = new Properties();

        try {
            propietaBuild.load(MainGUI.class.getClassLoader().getResourceAsStream("pom.properties"));
        }catch(Exception ignored){}

        numVersion = propietaBuild.getProperty("project.version");
        buildDate = propietaBuild.getProperty("project.build");
        appName = "CalendarioRRR";

        aboutStage = new Stage();
        aboutStage.setTitle(resourceBundle.getString("aboutStage.testo"));
        aboutStage.setAlwaysOnTop(true);
        aboutStage.initModality(Modality.APPLICATION_MODAL);

        BorderPane borderAbout = new BorderPane();
        VBox vbAbout = new VBox();
        VBox appInfo = new VBox();

        Label version = new Label();
        Label buildDateLabel = new Label();
        Label app_name = new Label();

        Label dev = new Label();
        Label rav = new Label();
        Label ron = new Label();
        Label ric = new Label();

        version.setText(resourceBundle.getString("aboutVer.testo") + " = " + numVersion);
        buildDateLabel.setText(resourceBundle.getString("aboutDate.testo") + " = " + buildDate);
        app_name.setText(resourceBundle.getString("aboutName.testo") + " = " + appName);

        dev.setText(resourceBundle.getString("aboutDev.testo") + ": ");
        rav.setText("Davide Ravani  davide.ravani@student.supsi.ch");
        ron.setText("Lorenzo Ronzani  lorenzo.ronzani@student.supsi.ch");
        ric.setText("Andrea Riccardi  andrea.riccardi@student.supsi.ch");

        appInfo.getChildren().addAll(app_name, version, buildDateLabel);
        vbAbout.getChildren().addAll(dev, rav, ron, ric);
        vbAbout.setAlignment(Pos.CENTER_LEFT);
        appInfo.setAlignment(Pos.CENTER_LEFT);

        borderAbout.setTop(appInfo);
        borderAbout.setCenter(vbAbout);
        scena = new Scene(borderAbout, 400, 150);

        aboutStage.setScene(scena);
    }

    public Stage getAboutStage() {
        return aboutStage;
    }
}
