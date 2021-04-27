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

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.skin.DatePickerSkin;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;

import javafx.stage.Stage;
import javafx.util.Callback;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Date;


public class Gui extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        try {
            //setto titolo come slide
            LocalDate dataOra = LocalDate.now();
            stage.setTitle("Finestra principale calendario ("+dataOra.getMonth().toString().substring(0,1)+""+dataOra.getMonth().toString().substring(1).toLowerCase()+" "+ dataOra.getYear()+")");

            BorderPane root = new BorderPane();
            Scene scene = new Scene(root, 800, 400);
            //scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

            //creo il datepicker che rimane costantemente mostrato
            DatePicker datePicker = new DatePicker(LocalDate.now());

            DatePickerSkin datePickerSkin = new DatePickerSkin(datePicker);
            Node popupContent = datePickerSkin.getPopupContent();


            //Da questo metodo viene gestita la questione della creazione degli eventi da metter nella cosa
            //QUi dentro quindi metti il codice per lanciare la tua nuova finesta
            datePicker.setOnAction(event -> {
                LocalDate date = datePicker.getValue();
                System.out.println(("Selected date: " + date));
                //DateCell cell = datePicker.getDayCellFactory();


                Stage modalStage = new Stage();
                GridPane modal = new GridPane();
                modalStage.setScene(new Scene(modal, 300, 400));
                modalStage.setTitle("My modal window");
                modalStage.initModality(Modality.WINDOW_MODAL);

                //Controllare a cosa serve
                //modalStage.initOwner(((Node)event.getSource()).getScene().getWindow() );

                TextField nomeEventoInput = new TextField();
                Label nomeEvento = new Label();
                nomeEvento.setText("test");

                modal.setAlignment(Pos.TOP_LEFT);
                modal.getChildren().add(nomeEventoInput);
                modal.setAlignment(Pos.TOP_RIGHT);
                modal.getChildren().add(nomeEvento);


                modalStage.show();
            });

            //show
            root.setCenter(popupContent);
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            //e.printStackTrace();
        }
    }



    public static void main(String[] args) {
        launch(args);
    }
}
