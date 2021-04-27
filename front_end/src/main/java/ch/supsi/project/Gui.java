package ch.supsi.project;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Gui extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Calendario");

        BorderPane root = new BorderPane();

        stage.setScene(new Scene(root,900,700));
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
