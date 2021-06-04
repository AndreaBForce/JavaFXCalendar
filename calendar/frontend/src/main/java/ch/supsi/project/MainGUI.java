package ch.supsi.project;

import ch.supsi.project.service_layer.CalendarContainer;
import ch.supsi.project.service_layer.Event;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

import static java.util.Calendar.*;

public class MainGUI extends Application {
    static LocalDate dataOra = LocalDate.now();
    CalendarContainer calendario = new CalendarContainer("Prova.txt");
    BorderPane setupCalendario = new BorderPane();
    ResourceBundle resourceBundle;

    @Override
    public void start(Stage stage) throws Exception {
        //TODO controllare mese perchè non ho individuato dove viene stampato
        Locale.setDefault(Locale.ENGLISH);
        resourceBundle = ResourceBundle.getBundle("i18n/stringhe");

        //Parte della gestione delle propieta di build e di versione
        Properties propietaBuild = new Properties();

        try {
            propietaBuild.load(MainGUI.class.getClassLoader().getResourceAsStream("pom.properties"));
        }catch(Exception e){}

        String NUM_VERSION = propietaBuild.getProperty("project.version");
        String BUILD_DATE = propietaBuild.getProperty("project.build");
        String APP_NAME = "CalendarioRRR";

        try {
            /**
             * STAGE PRINCIPALE
             */
            stage.setTitle(resourceBundle.getString("stageTitle.testo") + " (" + dataOra.getMonth().toString().substring(0, 1) + "" + dataOra.getMonth().toString().substring(1).toLowerCase() + " " + dataOra.getYear() + ")");

            /**
             * ROOT
             */
            //Inizializzo border pane principale
            BorderPane root = new BorderPane();
            root.setPrefSize(900, 700);
            //root = FXMLLoader.load(getClass().getResource("/fxml/Scene.fxml"),resourceBundle);

            /**
             * TOP
             */
            //creo hbox top con il menu blue nel top border pane
            HBox top = new HBox();
            top.setPadding(new Insets(5, 5, 5, 5));
            top.setSpacing(10);
            top.setStyle("-fx-background-color: #336699;");

            //Menu tendina file -> exit
            Menu menuFile = new Menu(resourceBundle.getString("menuFile.testo"));
            MenuItem menuExit = new MenuItem(resourceBundle.getString("menuExit.testo"));
            menuFile.getItems().add(menuExit);
            MenuBar menuBar1 = new MenuBar();
            menuBar1.getMenus().add(menuFile);

            //Menu tendina edit -> new.. edit->previous edit->next
            Menu menuEdit = new Menu(resourceBundle.getString("menuEdit.testo"));
            MenuItem menuNew = new MenuItem(resourceBundle.getString("menuNew.testo"));
            MenuItem menuPrev = new MenuItem(resourceBundle.getString("menuPrev.testo"));
            MenuItem menuNext = new MenuItem(resourceBundle.getString("menuNext.testo"));

            menuEdit.getItems().addAll(menuNew, menuPrev, menuNext);
            MenuBar menuBar2 = new MenuBar();
            menuBar2.getMenus().add(menuEdit);

            //Menu tendina help -> about
            Menu menuHelp = new Menu(resourceBundle.getString("menuHelp.testo"));
            MenuItem menuAbout = new MenuItem(resourceBundle.getString("menuAbout.testo"));
            menuHelp.getItems().add(menuAbout);
            MenuBar menuBar3 = new MenuBar();
            menuBar3.getMenus().add(menuHelp);

            //aggiungo il menubar alla parte top del socio
            top.getChildren().addAll(menuBar1, menuBar2, menuBar3);
            /**
             * FINE TOP
             */

            /**
             * CENTER
             */
            //Creo bottoni << >>
            HBox centerMen = new HBox();
            centerMen.setPadding(new Insets(5, 5, 5, 5));
            centerMen.setSpacing(10);

            Button monthPrev = new Button();
            Button monthNext = new Button();
            Label monthCurrent = new Label();

            monthCurrent.setText(dataOra.getMonth().toString() + " " + dataOra.getYear());
            monthCurrent.setFont(new Font("Arial", 20));

            monthNext.setText(">>");
            monthPrev.setText("<<");

            centerMen.getChildren().addAll(monthPrev, monthCurrent, monthNext);
            centerMen.setAlignment(Pos.TOP_CENTER);
            /**
             * FINE CENTER
             */

            /**
             * BOTTOM
             */

            //Qua avviene la creazione del calendario ovvero con il gridpane di ritorno
            setupCalendario.setCenter(updateCalendario(calendario,setupCalendario));

            /**
             * FINE BOTTOM
             */

            root.setTop(top);
            root.setCenter(centerMen);
            root.setBottom(setupCalendario);
            /**
             * FINE ROOT
             */

            /**
             * FINE STAGE PRINCIPALE
             */

            /**
             * SCENE
             */
            //mostro la finestra principale
            Scene scene = new Scene(root, 940, 900);
            stage.setScene(scene);

            stage.setMinHeight(900);
            stage.setMinWidth(940);

            stage.setMaxHeight(980);
            stage.setMaxWidth(980);

            stage.show();
            /**
             * FINE SCENE
             */

            /**
             * STAGE EXIT
             */
            //Creo parte per gestione del modale dell'exit
            //quindi un bottone con sei sicuro di voler uscire
            Stage exitStage = new Stage();
            exitStage.setTitle(resourceBundle.getString("exitStage.testo"));
            exitStage.setAlwaysOnTop(true);
            //borderpane padre
            BorderPane exitBorder = new BorderPane();
            //Label di sicurezza
            Label sicuro = new Label();
            sicuro.setText(resourceBundle.getString("sure.testo"));
            sicuro.setFont(new Font("Arial", 15));
            sicuro.setAlignment(Pos.CENTER);
            exitBorder.setTop(sicuro);
            HBox exitButtons = new HBox();
            Button exitAnnulla = new Button();
            Button exitEsci = new Button();
            exitEsci.setText(resourceBundle.getString("exitExit.testo"));
            exitAnnulla.setText(resourceBundle.getString("exitCancel.testo"));
            exitEsci.setStyle("-fx-background-color: #336699; ");

            exitButtons.setSpacing(20);
            exitAnnulla.setPrefSize(70, 30);
            exitEsci.setPrefSize(70, 30);
            exitButtons.getChildren().addAll(exitEsci, exitAnnulla);
            exitButtons.setAlignment(Pos.CENTER);
            exitBorder.setCenter(exitButtons);

            //scena del meno di exit con annulla e socio
            Scene scenaE = new Scene(exitBorder, 300, 100);
            /**
             * FINE STAGE EXIT
             */

            /**
             * STAGE VERSIONE
             */
            //Creo la parte della gestione della versione
            Stage aboutStage = new Stage();
            aboutStage.setTitle(resourceBundle.getString("aboutStage.testo"));
            aboutStage.setAlwaysOnTop(true);
            BorderPane borderAbout = new BorderPane();
            VBox vbAbout = new VBox();
            VBox appInfo = new VBox();

            Label version = new Label();
            Label buildDate = new Label();
            Label app_name = new Label();

            Label dev = new Label();
            Label rav = new Label();
            Label ron = new Label();
            Label ric = new Label();

            version.setText(resourceBundle.getString("aboutVer.testo") + " = " + NUM_VERSION);
            buildDate.setText(resourceBundle.getString("aboutDate.testo") + " = " + BUILD_DATE);
            app_name.setText(resourceBundle.getString("aboutName.testo") + " = " + APP_NAME);

            dev.setText(resourceBundle.getString("aboutDev.testo") + ": ");
            rav.setText("Davide Ravani  davide.ravani@student.supsi.ch");
            ron.setText("Lorenzo Ronzani  lorenzo.ronzani@student.supsi.ch");
            ric.setText("Andrea Riccardi  andrea.riccardi@student.supsi.ch");

            appInfo.getChildren().addAll(app_name, version, buildDate);
            vbAbout.getChildren().addAll(dev, rav, ron, ric);
            vbAbout.setAlignment(Pos.CENTER_LEFT);
            appInfo.setAlignment(Pos.CENTER_LEFT);

            borderAbout.setTop(appInfo);
            borderAbout.setCenter(vbAbout);
            Scene aboutScene = new Scene(borderAbout, 400, 150);
            /**
             * FINE STAGE VERSIONE
             */

            /**
             * LISTENER
             */
            /**
             * TOP
             */
            menuExit.setOnAction(mouse -> {
                //TODO FIXARE QUI CHE LANCIA ECCEZIONE
                //Cannot set modality once stage has been set visible
                //Avviene quando si chiude la finestra di exit con annulla o con la x
                //e poi dopo quando si vuole uscire non va e lancia eccezione
                exitStage.setScene(scenaE);
                exitStage.initModality(Modality.APPLICATION_MODAL);

                exitStage.showAndWait();
            });

            exitAnnulla.setOnMouseClicked(x -> {
                exitStage.close();
            });

            exitEsci.setOnAction(x -> {
                exitStage.close();
                stage.close();
            });

            //action event dei menu
            menuPrev.setOnAction(mouseEvent -> {
                dataOra = dataOra.minusMonths(1);
                monthCurrent.setText(dataOra.getMonth().toString() + " " + dataOra.getYear());
                setupCalendario.setCenter(updateCalendario(calendario,setupCalendario));
            });

            //Sono gli action event dei menu
            menuNext.setOnAction(mouseEvent -> {
                dataOra = dataOra.plusMonths(1);
                monthCurrent.setText(dataOra.getMonth().toString() + " " + dataOra.getYear());
                setupCalendario.setCenter(updateCalendario(calendario,setupCalendario));
            });

            menuNew.setOnAction(mouse -> {
                newEventModal(dataOra,setupCalendario);
                setupCalendario.setCenter(updateCalendario(calendario,setupCalendario));
            });

            menuAbout.setOnAction(x -> {
                aboutStage.setScene(aboutScene);
                aboutStage.initModality(Modality.APPLICATION_MODAL);
                aboutStage.showAndWait();
            });
            /**
             * CENTER
             */
            EventHandler<MouseEvent> clickNextMonth = new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    dataOra = dataOra.plusMonths(1);
                    monthCurrent.setText(dataOra.getMonth().toString() + " " + dataOra.getYear());
                    setupCalendario.setCenter(updateCalendario(calendario,setupCalendario));
                }
            };

            EventHandler<MouseEvent> clickPrevMonth = new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    dataOra = dataOra.minusMonths(1);
                    monthCurrent.setText(dataOra.getMonth().toString() + " " + dataOra.getYear());
                    setupCalendario.setCenter(updateCalendario(calendario,setupCalendario));
                }
            };

            monthNext.setOnMouseClicked(clickNextMonth);
            monthPrev.setOnMouseClicked(clickPrevMonth);
            /**
             * FINE LISTENER
             */

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//socio che refresha il calendario
    private GridPane updateCalendario(CalendarContainer calendario,BorderPane setupCalendario) {
        GridPane calendar = new GridPane();
        List<Event> tmpCal = calendario.getCalendar();

        //Gestione apici calendario
        Label[] daysOfTheWeek = {
                new Label(resourceBundle.getString("monday.testo")),
                new Label(resourceBundle.getString("tuesday.testo")),
                new Label(resourceBundle.getString("wednesday.testo")),
                new Label(resourceBundle.getString("thursday.testo")),
                new Label(resourceBundle.getString("friday.testo")),
                new Label(resourceBundle.getString("saturday.testo")),
                new Label(resourceBundle.getString("sunday.testo")),
        };

        for (int i = 0; i < daysOfTheWeek.length; i++) {
            daysOfTheWeek[i].setStyle("-fx-font-weight: bold");
            daysOfTheWeek[i].setFont(new Font("Arial", 15));

            calendar.add(daysOfTheWeek[i], i+1, 0);
        }

        boolean beforeMonth = true;
        boolean afterMonth = false;

        Calendar start = Calendar.getInstance();

        LocalDate date = LocalDate.of(dataOra.getYear(), dataOra.getMonth(), 1);
        LocalDate firstMonday = date.with(TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY));
        start.set(DAY_OF_MONTH, firstMonday.getDayOfMonth());
        if(start.get(DAY_OF_MONTH) != 1)
            start.add(DATE, -7);
        else
            beforeMonth = false;

        int i = 1;
        int j = 1;
        int count = 0;
        while (count < 42) {
            Calendar dum = Calendar.getInstance();


            List<Event> eventsOfToday = new ArrayList<>();
            for (Event e : tmpCal) {
                dum.set(DATE, e.getDay().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getDayOfMonth());

                if (dum.get(DAY_OF_MONTH) == start.get(DAY_OF_MONTH)
                        && dum.get(MONTH) == start.get(MONTH)
                        && dum.get(YEAR) == start.get(YEAR)){
                    eventsOfToday.add(e);
                }
            }

            Cell c = new Cell(130, start, eventsOfToday, resourceBundle);

            if(beforeMonth){
                c.isDayOfCurrMonth(false);
            } else c.isDayOfCurrMonth(!afterMonth);

            //popolaGriglia(mesePrima-dayofWeek+count,Cell.ft,tmpCal,Cell.ora,anno,mese-1,eventiVerticali);

            calendar.add(c, i, j);

            if(start.getActualMaximum(DAY_OF_MONTH) == start.get(DAY_OF_MONTH) && beforeMonth){
                beforeMonth = false;
            } else if(start.getActualMaximum(DAY_OF_MONTH) == start.get(DAY_OF_MONTH) && !beforeMonth){
                afterMonth = true;
            }

            if (i % 7 == 0) {   // last day of the week
                i = 1;
                j++;
            } else {
                i++;
            }

            count++;
            start.add(DATE, 1);
        }

        calendar.setAlignment(Pos.TOP_CENTER);
        return calendar;
    }

    private static String printCalendar(Calendar c) {
        return Cell.ft.format(c.getTime());
    }

    private void newEventModal(LocalDate date,BorderPane setupCalendario) {
        Stage modalStage = new Stage();
        modalStage.setAlwaysOnTop(true);

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
        modalStage.setTitle(date.toString());
        modalStage.initModality(Modality.WINDOW_MODAL);

        Alert alert = new Alert(Alert.AlertType.WARNING, resourceBundle.getString("eventAlert.testo"));
        // Necessari per mostrare alert davanti al modal
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(modalStage);

        //Controllare a cosa serve
        //modalStage.initOwner(((Node)event.getSource()).getScene().getWindow() );

        TextField nomeEventoInput = new TextField();
        Label nomeEvento = new Label(resourceBundle.getString("eventTitle.testo"));

        DatePicker datePicker = new DatePicker();
        datePicker.setValue(date);
        Label selezioneData = new Label(resourceBundle.getString("eventDate.testo"));

        final ObservableList<String> appTimepicker = FXCollections.observableArrayList();
        ArrayList<Calendar> orari = new ArrayList<>();

        Calendar ora = new GregorianCalendar();
        ora.setTime(Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant()));
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
        orari.stream().forEach(c -> appTimepicker.add(format.format(c.getTime())));

        Label selezioneOrario = new Label(resourceBundle.getString("eventTimeSelect.testo"));
        ListView timepickerStart = new ListView(appTimepicker);
        ListView timepickerEnd = new ListView(appTimepicker);

        final ObservableList appTypePicker = FXCollections.observableArrayList();

        CalendarContainer.eventTypeList.stream().forEach(e -> appTypePicker.add(e.getDescription()));

        Label selezioneTipoEvento = new Label(resourceBundle.getString("eventTypeSelect.testo"));
        ListView typepicker = new ListView(appTypePicker);

        Button create = new Button(resourceBundle.getString("eventCreate.testo"));

        create.setOnMouseClicked(mouse -> {
            if(nomeEventoInput.getText().isEmpty()
                    || datePicker.getValue() == null
                    || timepickerStart.getSelectionModel().selectionModeProperty().isNull().get()
                    || timepickerEnd.getSelectionModel().selectionModeProperty().isNull().get()){

                alert.initModality(Modality.APPLICATION_MODAL);
                alert.showAndWait();
            }else{
                Date time = new Date();
                Calendar cal = Calendar.getInstance();
                cal.setTime(time);
                cal.add(Calendar.HOUR_OF_DAY, 3);
                long start = cal.getTime().getTime();
                cal.add(Calendar.HOUR_OF_DAY, 3);
                long end = cal.getTime().getTime();
                //calendario.addEvent(new Event("testtest",time.getTime(),start,end, eventTypeList.get(1)));

                calendario.addEvent(new Event(nomeEventoInput.getText(), Date.from(datePicker.getValue().atStartOfDay()
                        .atZone(ZoneId.systemDefault()).toInstant()).getTime(), orari.get(timepickerStart.getSelectionModel().getSelectedIndex()).getTime().getTime(), orari.get(timepickerEnd.getSelectionModel().getSelectedIndex()).getTime().getTime(), CalendarContainer.eventTypeList.get(typepicker.getSelectionModel().getSelectedIndex())));
                setupCalendario.setCenter(updateCalendario(calendario,setupCalendario));
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

        modalStage.initModality(Modality.APPLICATION_MODAL);
        modalStage.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);

    }
}
