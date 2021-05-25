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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class MainGUI extends Application {
    static LocalDate dataOra = LocalDate.now();
    CalendarContainer calendario = new CalendarContainer("Prova.txt");
    BorderPane setupCalendario = new BorderPane();

    @Override
    public void start(Stage stage) throws Exception {
        //VERSIONE DI BUILD (DA AGGIORNARE OGNI VOLTA)
        //Sono i dati che compaiono nel about
        //TODO DA RIMPIAZZARE CON LA VARIABILE BUILD
        String NUM_VERSION = "1.5.0.2";
        String BUILD_DATE = "07-05-2021";
        String APP_NAME = "CalendarioRRR";

        try {
            /**
             * STAGE PRINCIPALE
             */
            stage.setTitle("Finestra principale calendario (" + dataOra.getMonth().toString().substring(0, 1) + "" + dataOra.getMonth().toString().substring(1).toLowerCase() + " " + dataOra.getYear() + ")");

            /**
             * ROOT
             */
            //Inizializzo border pane principale
            BorderPane root = new BorderPane();
            root.setPrefSize(900, 700);

            /**
             * TOP
             */
            //creo hbox top con il menu blue nel top border pane
            HBox top = new HBox();
            top.setPadding(new Insets(5, 5, 5, 5));
            top.setSpacing(10);
            top.setStyle("-fx-background-color: #336699;");

            //Menu tendina file -> exit
            Menu menuFile = new Menu("File");
            MenuItem menuExit = new MenuItem("Exit");
            menuFile.getItems().add(menuExit);
            MenuBar menuBar1 = new MenuBar();
            menuBar1.getMenus().add(menuFile);

            //Menu tendina edit -> new.. edit->previous edit->next
            Menu menuEdit = new Menu("Edit");
            MenuItem menuNew = new MenuItem("New...");
            MenuItem menuPrev = new MenuItem("Previous");
            MenuItem menuNext = new MenuItem("Next");

            menuEdit.getItems().addAll(menuNew, menuPrev, menuNext);
            MenuBar menuBar2 = new MenuBar();
            menuBar2.getMenus().add(menuEdit);

            //Menu tendina help -> about
            Menu menuHelp = new Menu("Help");
            MenuItem menuAbout = new MenuItem("About");
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
            //Creo bottoni
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

            //Qua avviene la creazione del calendario
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
            Scene scene = new Scene(root, 1100, 1000);
            stage.setScene(scene);
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
            exitStage.setTitle("Confirm Exit");
            exitStage.setAlwaysOnTop(true);
            //borderpane padre
            BorderPane exitBorder = new BorderPane();
            //Label di sicurezza
            Label sicuro = new Label();
            sicuro.setText("Are you sure you want to exit?");
            sicuro.setFont(new Font("Arial", 15));
            sicuro.setAlignment(Pos.CENTER);
            exitBorder.setTop(sicuro);
            HBox exitButtons = new HBox();
            Button exitAnnulla = new Button();
            Button exitEsci = new Button();
            exitEsci.setText("Exit");
            exitAnnulla.setText("Cancel");
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
            aboutStage.setTitle("About");
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

            version.setText("Current Version = " + NUM_VERSION);
            buildDate.setText("Build date = " + BUILD_DATE);
            app_name.setText("App name = " + APP_NAME);

            dev.setText("Developed by: ");
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
                exitStage.setScene(scenaE);
                exitStage.show();
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
                aboutStage.show();
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
    public GridPane updateCalendario(CalendarContainer calendario,BorderPane setupCalendario) {
        GridPane calendar = new GridPane();
        List<Event> tmpCal = calendario.getCalendar();

        //Gestione apici calendario

        Label lun = new Label();
        lun.setText("Monday");
        lun.setStyle("-fx-font-weight: bold");
        lun.setFont(new Font("Arial", 15));

        Label dom = new Label();
        dom.setText("Sunday");
        dom.setStyle("-fx-font-weight: bold");
        dom.setFont(new Font("Arial", 15));

        Label mart = new Label();
        mart.setText("Tuesday");
        mart.setStyle("-fx-font-weight: bold");
        mart.setFont(new Font("Arial", 15));

        Label merc = new Label();
        merc.setText("Wednesday");
        merc.setStyle("-fx-font-weight: bold");
        merc.setFont(new Font("Arial", 15));

        Label giov = new Label();
        giov.setText("Thursday");
        giov.setStyle("-fx-font-weight: bold");
        giov.setFont(new Font("Arial", 15));

        Label vene = new Label();
        vene.setText("Friday");
        vene.setStyle("-fx-font-weight: bold");
        vene.setFont(new Font("Arial", 15));

        Label sab = new Label();
        sab.setText("Saturday");
        sab.setStyle("-fx-font-weight: bold");
        sab.setFont(new Font("Arial", 15));

        calendar.add(dom, 1, 0);
        calendar.add(lun, 2, 0);
        calendar.add(mart, 3, 0);
        calendar.add(merc, 4, 0);
        calendar.add(giov, 5, 0);
        calendar.add(vene, 6,0);
        calendar.add(sab, 7,0);


        double s = 130; // side of rectangle
        LocalDate start = dataOra.withDayOfMonth(1);
        int dayofWeek = start.getDayOfWeek().getValue();
        int monthlen = start.lengthOfMonth();
        int mese = start.getMonth().getValue();
        int anno = start.getYear();
        int count = 1, datesettter = 1,mesePrima = start.minusMonths(1).lengthOfMonth(),meseDopo = 1;;

        //date formatter
        SimpleDateFormat ft = new SimpleDateFormat("yyyy.MM.dd");
        SimpleDateFormat ora = new SimpleDateFormat("HH:mm");

        //Ciclo for che crea la scacchiera del mese
        for (int i = 1; i < 7; i++) {
            for (int j = 1; j < 8; j++) {
                BorderPane casella = new BorderPane();
                BorderPane casella_text = new BorderPane();
                Label num_day = new Label();
                num_day.setStyle("-fx-font-weight: bold");

                VBox eventiVerticali = new VBox();

                Rectangle r = new Rectangle(s, s, s, s);

                //setto background socio bianco e bordi
                r.setFill(Color.WHITE);
                r.setStyle("-fx-stroke: black; -fx-stroke-width: 2;");

                if (count <= dayofWeek) {
                    //Qua gestisco la parte dei giorni prima del mese
                    num_day.setText("  "+(mesePrima-dayofWeek+count));

                    popolaGriglia(mesePrima-dayofWeek+count,ft,tmpCal,ora,anno,mese-1,eventiVerticali);

                    r.setFill(Color.GRAY);
                    r.setStyle("-fx-stroke: black; -fx-stroke-width: 2;");

                } else if (datesettter <= monthlen) {
                    num_day.setText("  " + (datesettter));

                    popolaGriglia(datesettter,ft,tmpCal,ora,anno,mese,eventiVerticali);

                    datesettter++;
                } else {
                    num_day.setText("  "+(meseDopo));

                    popolaGriglia(meseDopo,ft,tmpCal,ora,anno,mese+1,eventiVerticali);
                    meseDopo++;
                    r.setFill(Color.GRAY);
                    r.setStyle("-fx-stroke: black; -fx-stroke-width: 2;");
                }

                count++;

                casella.setCenter(r);
                num_day.setAlignment(Pos.TOP_LEFT);

                casella_text.setTop(num_day);

                StackPane cella = new StackPane(casella, casella_text);

                if (!num_day.getText().equals("")) {
                    final LocalDate date = start.plusDays(datesettter - 2);
                    //da metter qua dentro la gestione migliore della data
                    cella.setOnMouseClicked(mouseEvent -> {
                        if (mouseEvent.getClickCount() == 2) {
                            newEventModal(date,setupCalendario);

                        }
                    });
                }

                eventiVerticali.setAlignment(Pos.TOP_CENTER);
                casella_text.setCenter(eventiVerticali);

                calendar.add(cella, j, i);
            }
        }

        calendar.setAlignment(Pos.TOP_CENTER);
        return calendar;
    }

    void popolaGriglia(int datesettter,SimpleDateFormat ft,List<Event> tmpCal,SimpleDateFormat ora,int anno,int mese,VBox eventiVerticali){

        for (Event e: tmpCal) {
            int annoD = Integer.valueOf(ft.format(e.getDay()).substring(0,4));
            int meseD = Integer.valueOf(ft.format(e.getDay()).substring(5,7));
            int giornoD = Integer.valueOf(ft.format(e.getDay()).substring(8,10));

            //bruttissimo if che controlla se la data è la seguente
            //In futuro andra gestito con Date cosi basta solo un compare
            if (anno == annoD) {
                if (meseD == mese) {
                    if (giornoD == datesettter) {
                        HBox hBoxDay = new HBox();
                        hBoxDay.setSpacing(5);
                        String titolo = e.getTitle();
                        hBoxDay.setStyle("-fx-background-color: " + e.getType().getColour().getHexCode() + ";");
                        Label impegno = new Label();
                        Label orario = new Label();
                        orario.setText(" " + ora.format(e.getStart()) + " - " + ora.format(e.getEnd()));

                        if (titolo.length() > 4) {
                            impegno.setText(titolo.substring(0, 4));
                        } else {
                            impegno.setText(e.getTitle());
                        }
                        hBoxDay.getChildren().addAll(impegno, orario);
                        hBoxDay.setAlignment(Pos.CENTER);
                        eventiVerticali.getChildren().add(hBoxDay);


                        hBoxDay.setOnMouseClicked(mouseEvent -> {
                            Stage mostrami = new Stage();
                            mostrami.setAlwaysOnTop(true);
                            mostrami.setTitle("Evento selezionato");

                            BorderPane mostraDati = new BorderPane();
                            VBox dati = new VBox();
                            Label nome = new Label();
                            Label data = new Label();
                            Label startE = new Label();
                            Label end = new Label();
                            Label importanzaE = new Label();
                            Label coloreE = new Label();
                            nome.setText("Titolo evento: " + e.getTitle());
                            data.setText("Data evento: " + e.getDay().toString());
                            startE.setText("Orario inizio: " + ora.format(e.getStart()));
                            end.setText("Orario fine: " + ora.format(e.getEnd()));

                            importanzaE.setText("Tipo evento: "+e.getType().toString());
                            coloreE.setText("                                                                                                       ");
                            coloreE.setStyle("-fx-background-color: " + e.getType().getColour() + ";");
                            dati.getChildren().addAll(nome, data, startE, end, importanzaE,coloreE);
                            mostraDati.setCenter(dati);

                            Scene scenaEvento = new Scene(mostraDati, 400, 100);
                            mostrami.setScene(scenaEvento);

                            mostrami.show();
                        });

                    }
                }
            }
        }

    }


    public void newEventModal(LocalDate date,BorderPane setupCalendario) {
        Stage modalStage = new Stage();
        modalStage.setAlwaysOnTop(true);

        GridPane modal = new GridPane();
        modal.setPrefSize(400, 800);
        modal.setHgap(10);
        modal.setVgap(10);
        modal.setPadding(new Insets(10, 10, 10, 10));
        //modal.setGridLinesVisible(true);

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



        modalStage.setScene(new Scene(modal, 300, 400));
        modalStage.setTitle(date.toString());
        modalStage.initModality(Modality.WINDOW_MODAL);

        Alert alert = new Alert(Alert.AlertType.WARNING, "Missing one or more fields.");
        // Necessari per mostrare alert davanti al modal
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(modalStage);

        //Controllare a cosa serve
        //modalStage.initOwner(((Node)event.getSource()).getScene().getWindow() );

        DatePicker datePicker = new DatePicker();
        datePicker.setValue(date);
        TextField nomeEventoInput = new TextField();
        Label nomeEvento = new Label("Nome evento");

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
            ora.set(Calendar.MINUTE, ora.get(Calendar.MINUTE) + 15);
            if (ora.get(Calendar.HOUR_OF_DAY) == 0 && ora.get(Calendar.MINUTE) == 0) {
                endCheck = true;
            }
        }
        orari.stream().forEach(c -> appTimepicker.add(format.format(c.getTime())));

        Label selezioneOrario = new Label("Selezione orario");
        ListView timepickerStart = new ListView(appTimepicker);
        ListView timepickerEnd = new ListView(appTimepicker);

        final ObservableList appTypePicker = FXCollections.observableArrayList();

        CalendarContainer.eventTypeList.stream().forEach(e -> appTypePicker.add(e.getDescription()));

        Label selezioneTipoEvento = new Label("Selezione tipo evento");
        ListView typepicker = new ListView(appTypePicker);

        Button create = new Button("Create");

        create.setOnMouseClicked(mouse -> {
            if(nomeEventoInput.getText().isEmpty()
                    || datePicker.getValue() == null
                    || timepickerStart.getSelectionModel().selectionModeProperty().isNull().get()
                    || timepickerEnd.getSelectionModel().selectionModeProperty().isNull().get()){

                alert.show();
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
        modal.add(datePicker, 1, 1);
        modal.add(selezioneOrario, 0, 2);
        modal.add(timepickerStart, 1, 2);
        modal.add(timepickerEnd, 2, 2);
        modal.add(typepicker, 1, 3);
        modal.add(create, 1, 4);

        modalStage.show();
    }

    public static void main(String[] args) {
        launch(args);

    }
}
