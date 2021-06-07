package ch.supsi.project.View;

import ch.supsi.project.Controller.CalendarViewController;
import ch.supsi.project.applicationlayer.CalendarController;
import ch.supsi.project.model.Event;
import ch.supsi.project.model.EventType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class New {
    private ResourceBundle resourceBundle;
    private AlertEvent alertView;
    private LocalDate date;
    private CalendarViewController calendarViewController;
    private Stage modalStage;

    public New(ResourceBundle resourceBundle, Calendar date, CalendarViewController calendarViewController) {
        this.resourceBundle = resourceBundle;

        this.calendarViewController = calendarViewController;

        this.date = LocalDateTime.ofInstant(date.toInstant(), date.getTimeZone().toZoneId()).toLocalDate();

        modalStage = new Stage();

        alertView = new AlertEvent(resourceBundle);

        GridPane modal = new GridPane();
        modal.setPrefSize(600, 600);

        modal.setHgap(10);
        modal.setVgap(10);
        modal.setPadding(new Insets(10, 10, 10, 10));

        for (int k = 0; k < 3; k++) {
            ColumnConstraints colConst = new ColumnConstraints();
            colConst.setPercentWidth(100.0 / 3);
            modal.getColumnConstraints().add(colConst);
        }
        for (int k = 0; k < 5; k++) {
            RowConstraints rowConst = new RowConstraints();
            rowConst.setPercentHeight(100.0 / 5);
            modal.getRowConstraints().add(rowConst);
        }

        Scene scene = new Scene(modal);

        modalStage.setMinHeight(modal.getPrefHeight());
        modalStage.setMinWidth(modal.getPrefWidth());

        modalStage.setScene(scene);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");
        
        modalStage.setTitle(this.date.format(formatter));
        modalStage.initModality(Modality.WINDOW_MODAL);

        TextField nomeEventoInput = new TextField();
        Label nomeEvento = new Label(resourceBundle.getString("eventTitle.testo"));

        DatePicker datePicker = new DatePicker();
        datePicker.setValue(this.date);
        Label selezioneData = new Label(resourceBundle.getString("eventDate.testo"));

        final ObservableList<String> appTimepicker = FXCollections.observableArrayList();
        ArrayList<Calendar> orari = new ArrayList<>();

        Calendar ora = new GregorianCalendar();
        ora.setTime(Date.from(this.date.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        ora.set(Calendar.HOUR_OF_DAY, 0);
        ora.set(Calendar.MINUTE, 0);
        ora.set(Calendar.SECOND, 0);
        ora.set(Calendar.MILLISECOND, 0);
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");

        boolean endCheck = false;

        while (!endCheck) {
            Calendar c = new GregorianCalendar();
            c.setTime(ora.getTime());
            orari.add(c);
            ora.set(Calendar.MINUTE, ora.get(Calendar.MINUTE) + 30);
            if (ora.get(Calendar.HOUR_OF_DAY) == 0 && ora.get(Calendar.MINUTE) == 0) {
                endCheck = true;
            }
        }
        orari.forEach(c -> appTimepicker.add(format.format(c.getTime())));

        Label selezioneOrario = new Label(resourceBundle.getString("eventTimeSelect.testo"));
        ListView<String> timepickerStart = new ListView<>(appTimepicker);
        ListView<String> timepickerEnd = new ListView<>(appTimepicker);

        final ObservableList<String> appTypePicker = FXCollections.observableArrayList();

        //Qua aggiungo gli eventi
        for (EventType e: CalendarController.eventTypeList) {
            appTypePicker.add(getNomeEventoPulito(e));
        }

        Label selezioneTipoEvento = new Label(resourceBundle.getString("eventTypeSelect.testo"));
        ListView<String> typepicker = new ListView<>(appTypePicker);

        typepicker.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            int i = -1;
            @Override
            public ListCell<String> call(ListView<String> param) {
                return new ListCell<String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);

                        if (item == null || empty) {
                            setText(null);
                        }else {
                            setText(item);

                            if(i == -1){
                                i++;
                            }else if(i < CalendarController.eventTypeList.size()) {
                                EventType e = CalendarController.eventTypeList.get(i);

                                setStyle("-fx-control-inner-background:" + e.getColour().getHexCode() + ";");
                                i++;

                            }



                        }

                    }
                };
            }
        });

        Button create = new Button(resourceBundle.getString("eventCreate.testo"));

        create.setOnMouseClicked(mouse -> {
            if (nomeEventoInput.getText().isEmpty()
                    || datePicker.getValue() == null
                    || timepickerStart.getSelectionModel().selectionModeProperty().isNull().get()
                    || timepickerEnd.getSelectionModel().selectionModeProperty().isNull().get()) {

                alertView.getStage().showAndWait();
            } else {
                Event checker = calendarViewController.addEvent(new Event(
                        nomeEventoInput.getText(),
                        Date.from(datePicker.getValue().atStartOfDay()
                                .atZone(ZoneId.systemDefault())
                                .toInstant())
                                .getTime(),
                        orari.get(timepickerStart.getSelectionModel().getSelectedIndex()).getTime().getTime(),
                        orari.get(timepickerEnd.getSelectionModel().getSelectedIndex()).getTime().getTime(),
                        CalendarController.eventTypeList.get(typepicker.getSelectionModel().getSelectedIndex()))
                );

                BorderPane exitBorder = new BorderPane();
                //Label di sicurezza
                Label sicuro = new Label();
                sicuro.setText(resourceBundle.getString("alertEV.testo"));
                sicuro.setFont(new Font("Arial", 15));
                sicuro.setAlignment(Pos.CENTER);
                exitBorder.setTop(sicuro);
                HBox exitButtons = new HBox();
                Button exitEsci = new Button();
                exitEsci.setText(resourceBundle.getString("alExBt.testo"));

                exitEsci.setStyle("-fx-background-color: #336699; ");

                exitButtons.setSpacing(20);

                exitEsci.setPrefSize(70, 30);
                exitButtons.getChildren().addAll(exitEsci);
                exitButtons.setAlignment(Pos.CENTER);
                exitBorder.setCenter(exitButtons);

                exitEsci.setOnAction(x -> {
                    modalStage.close();
                    alertView.getStage().close();
                });

                //scena del meno di exit con annulla e socio
                Scene scenaE = new Scene(exitBorder, 400, 100);

                if (checker == null) {
                    alertView.getStage().setScene(scenaE);
                    alertView.getStage().showAndWait();
                    modalStage.close();
                }
                modalStage.close();
            }
        });

        modal.add(nomeEvento, 0, 0);
        modal.add(nomeEventoInput, 1, 0);
        modal.add(selezioneData, 0, 1);
        modal.add(datePicker, 1, 1);
        modal.add(selezioneOrario, 0, 2);
        modal.add(timepickerStart, 1, 2);
        modal.add(timepickerEnd, 2, 2);
        modal.add(selezioneTipoEvento, 0, 3);
        modal.add(typepicker, 1, 3);
        modal.add(create, 1, 4);
    }

    public String getNomeEventoPulito(EventType e){
        String evento = e.getDescription().toString().toLowerCase();
        return resourceBundle.getString(evento+".testo");
    }

    public Stage getModalStage() {
        return modalStage;
    }
}
