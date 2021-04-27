package ch.supsi.project;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class Gui extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Calendario");



        BorderPane root = new BorderPane();
        Scene main = new Scene(root, 900, 700);

        Button test = new Button("modal");
        test.setOnMouseClicked(e -> {
            Stage modalStage = new Stage();
            GridPane modal = new GridPane();
            modalStage.setScene(new Scene(modal, 300, 400));
            modalStage.setTitle("My modal window");
            modalStage.initModality(Modality.WINDOW_MODAL);
            modalStage.initOwner(
                    ((Node)e.getSource()).getScene().getWindow() );

            TextField nomeEventoInput = new TextField();
            Label nomeEvento = new Label();
            nomeEvento.setText("test");

            modal.setAlignment(Pos.TOP_LEFT);
            modal.getChildren().add(nomeEventoInput);
            modal.setAlignment(Pos.TOP_RIGHT);
            modal.getChildren().add(nomeEvento);


            modalStage.show();
        });

        root.setTop(test);

        stage.setScene(main);
        stage.show();
    }



    public static void main(String[] args) {
        launch(args);
    }
}
