package ch.supsi.project;

import ch.supsi.project.service_layer.EventType;
import javafx.application.Application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.skin.DatePickerSkin;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

import javafx.stage.Stage;
import javafx.util.Callback;

import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;


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
                modalStage.setAlwaysOnTop(true);

                GridPane modal = new GridPane();
                modal.setHgap(10);
                modal.setVgap(10);
                modal.setPadding(new Insets(10, 10, 10, 10));
                modal.setGridLinesVisible(true);

                for (int i = 0; i < 2; i++) {
                    ColumnConstraints colConst = new ColumnConstraints();
                    colConst.setPercentWidth(100.0 / 2);
                    modal.getColumnConstraints().add(colConst);
                }
                for (int i = 0; i < 3; i++) {
                    RowConstraints rowConst = new RowConstraints();
                    rowConst.setPercentHeight(100.0 / 3);
                    modal.getRowConstraints().add(rowConst);
                }

                modalStage.setScene(new Scene(modal, 300, 400));
                modalStage.setTitle("My modal window");
                modalStage.initModality(Modality.WINDOW_MODAL);

                //Controllare a cosa serve
                //modalStage.initOwner(((Node)event.getSource()).getScene().getWindow() );

                TextField nomeEventoInput = new TextField();
                Label nomeEvento = new Label("Nome evento");
                DatePicker datepicker = new DatePicker();


                modal.add(nomeEvento, 0, 0);
                modal.add(nomeEventoInput, 1, 0);
                modal.add(datepicker, 1, 1);

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
