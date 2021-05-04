package ch.supsi.project;

import ch.supsi.project.application_layer.Colour;
import ch.supsi.project.service_layer.CalendarContainer;
import ch.supsi.project.service_layer.Event;
import ch.supsi.project.service_layer.EventType;
import ch.supsi.project.service_layer.Type;
import javafx.application.Application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
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

import java.sql.SQLOutput;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class Gui extends Application {
    static LocalDate dataOra = LocalDate.now();
    @Override
    public void start(Stage stage) throws Exception {

        //PARTE FX GUI NUOVA VANNO IMPLEMENTATI I LISTENER
        CalendarContainer calendario = new CalendarContainer("Prova.txt");
        List<EventType> eventTypeList = new ArrayList<>();

        eventTypeList.add(new EventType(Type.LECTION, Colour.BLUE));
        eventTypeList.add(new EventType(Type.LABORATORY, Colour.RED));
        eventTypeList.add(new EventType(Type.EXAM, Colour.GREEN));
        eventTypeList.add(new EventType(Type.HOLIDAY, Colour.ORANGE));
        eventTypeList.add(new EventType(Type.OTHERS, Colour.PURPLE));

        Date time = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        cal.add(Calendar.HOUR_OF_DAY,3);
        long start = cal.getTime().getTime();
        cal.add(Calendar.HOUR_OF_DAY, 3);
        long end = cal.getTime().getTime();

        try {
            //setto titolo come slide

            stage.setTitle("Finestra principale calendario (" + dataOra.getMonth().toString().substring(0, 1) + "" + dataOra.getMonth().toString().substring(1).toLowerCase() + " " + dataOra.getYear() + ")");
            //Inizializzo border pane
            BorderPane root = new BorderPane();
            root.setPrefSize(900, 700);

            //creo hbox top con il menu top
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
            root.setTop(top);

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


            //Creo calendario
            BorderPane setupCalendario = new BorderPane();
            //setup giorni settimana
            HBox giorni = new HBox();

            Label lun = new Label();
            lun.setText("Monday");
            lun.setFont(new Font("Arial", 15));

            Label dom = new Label();
            dom.setText("Sunday");
            dom.setFont(new Font("Arial", 15));

            Label mart = new Label();
            mart.setText("Tuesday");
            mart.setFont(new Font("Arial", 15));

            Label merc = new Label();
            merc.setText("Wednesday");
            merc.setFont(new Font("Arial", 15));

            Label giov = new Label();
            giov.setText("Thursday");
            giov.setFont(new Font("Arial", 15));

            Label vene = new Label();
            vene.setText("Friday");
            vene.setFont(new Font("Arial", 15));

            Label sab = new Label();
            sab.setText("Saturday");
            sab.setFont(new Font("Arial", 15));

            //giorni.setAlignment(Pos.CENTER);
            giorni.setSpacing(100);
            giorni.getChildren().addAll(dom, lun, mart, merc, giov, vene, sab);

            setupCalendario.setTop(giorni);

            //PARTE GESTIONE EVENTI

            //GESTIONE DELLA ROBA DEL FORM
            EventHandler<MouseEvent> clickHandleCasella = new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    System.out.println("PREMUTO ILTASTO");
                }
            };

            EventHandler<MouseEvent> clickNextMonth = new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    dataOra = dataOra.plusMonths(1);
                    monthCurrent.setText(dataOra.getMonth().toString() + " " + dataOra.getYear());
                    setupCalendario.setCenter(updateCalendario(calendario));
                }
            };

            EventHandler<MouseEvent> clickPrevMonth = new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    dataOra = dataOra.minusMonths(1);
                    monthCurrent.setText(dataOra.getMonth().toString() + " " + dataOra.getYear());
                    setupCalendario.setCenter(updateCalendario(calendario));
                }
            };

            monthNext.setOnMouseClicked(clickNextMonth);
            monthPrev.setOnMouseClicked(clickPrevMonth);

            //action event dei menu
            menuPrev.setOnAction(mouseEvent -> {
                dataOra = dataOra.minusMonths(1);
                monthCurrent.setText(dataOra.getMonth().toString() + " " + dataOra.getYear());
                setupCalendario.setCenter(updateCalendario(calendario));
            });

            //Sono gli action event dei menu
            menuNext.setOnAction(mouseEvent -> {
                dataOra = dataOra.plusMonths(1);
                monthCurrent.setText(dataOra.getMonth().toString() + " " + dataOra.getYear());
                setupCalendario.setCenter(updateCalendario(calendario));
            });

            //Qua avviene la creazione del calendario
            setupCalendario.setCenter(updateCalendario(calendario));

            root.setCenter(centerMen);
            root.setBottom(setupCalendario);

            Scene scene = new Scene(root, 1100, 1000);
            stage.setScene(scene);
            stage.show();

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
            exitAnnulla.setPrefSize(70,30);
            exitEsci.setPrefSize(70,30);
            exitButtons.getChildren().addAll(exitEsci,exitAnnulla);
            exitButtons.setAlignment(Pos.CENTER);
            exitBorder.setCenter(exitButtons);

            Scene scenaE = new Scene(exitBorder,300,200);
            menuExit.setOnAction(mouse -> {
                exitStage.setScene(scenaE);
                exitStage.show();
            });

            exitAnnulla.setOnMouseClicked(x -> {
                exitStage.close();
            });

            exitEsci.setOnAction( x -> {
                exitStage.close();
                stage.close();
            });

            //Creo la parte della gestione della versione
            Stage aboutStage = new Stage();
            aboutStage.setTitle("About");
            aboutStage.setAlwaysOnTop(true);
            BorderPane borderAbout = new BorderPane();
            VBox vbAbout = new VBox();

            Label version = new Label();
            Label dev = new Label();
            Label rav = new Label();
            Label ron = new Label();
            Label ric = new Label();

            version.setText("Current Version = 1.1");
            dev.setText("Developed by: ");
            rav.setText("Davide Ravani  davide.ravani@student.supsi.ch");
            ron.setText("Lorenzo Ronzani  lorenzo.ronzani@student.supsi.ch");
            ric.setText("Andrea Riccardi  andrea.riccardi@student.supsi.ch");

            vbAbout.getChildren().addAll(dev,rav,ron,ric);
            vbAbout.setAlignment(Pos.CENTER_LEFT);
            version.setAlignment(Pos.CENTER_LEFT);
            borderAbout.setTop(version);
            borderAbout.setCenter(vbAbout);
            Scene aboutScene = new Scene(borderAbout,300,100);

            menuAbout.setOnAction(x -> {
                aboutStage.setScene(aboutScene);
                aboutStage.show();
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public GridPane updateCalendario(CalendarContainer calendario) {
        GridPane calendar = new GridPane();
        List<Event> tmpCal = calendario.getCalendar();

        double s = 150; // side of rectangle
        LocalDate start = dataOra.withDayOfMonth(1);
        int dayofWeek = start.getDayOfWeek().getValue();
        int monthlen = start.lengthOfMonth();
        int mese = start.getMonth().getValue();
        int anno = start.getYear();
        int count = 1, datesettter = 1;
        //date formatter
        SimpleDateFormat ft = new SimpleDateFormat("yyyy.MM.dd");
        SimpleDateFormat ora = new SimpleDateFormat ("hh:mm");
        int meseD,annoD,giornoD;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                BorderPane casella = new BorderPane();
                BorderPane casella_text = new BorderPane();
                Label num_day = new Label();
                VBox eventiVerticali = new VBox();

                Rectangle r = new Rectangle(s, s, s, s);

                //Questo if controlla se il mese inizi di domenica cosi non skippa la prima riga
                if(dayofWeek == 7){
                    if (datesettter <= monthlen) {
                        num_day.setText("  " + (datesettter));
                        //un po inefficente
                        //ciclo sugli eventi e li inserisco in una hbox e in una vbox per mostrarli
                        for (Event e: tmpCal) {
                            annoD = Integer.valueOf(ft.format(e.getDay()).substring(0,4));
                            meseD = Integer.valueOf(ft.format(e.getDay()).substring(5,7));
                            giornoD = Integer.valueOf(ft.format(e.getDay()).substring(8,10));

                            //bruttissimo if che controlla se la data è la seguente
                            //In futuro andra gestito con Date cosi basta solo un compare
                            if(anno == annoD){
                                if(meseD == mese){
                                    if(giornoD == datesettter){
                                        HBox hBoxDay = new HBox();
                                        hBoxDay.setSpacing(10);
                                        Label colore = new Label();
                                        colore.setPrefSize(10,10);
                                        colore.setStyle("-fx-background-color: "+e.getType().getColour()+";");
                                        Label impegno = new Label();
                                        Label orario = new Label();
                                        orario.setText(" "+ora.format(e.getStart())+" - "+ora.format(e.getEnd()));

                                        impegno.setText(e.getTitle());
                                        hBoxDay.getChildren().addAll(colore,impegno,orario);
                                        hBoxDay.setAlignment(Pos.CENTER);
                                        eventiVerticali.getChildren().add(hBoxDay);
                                    }
                                }
                            }
                        }
                        datesettter++;
                    }
                }else {
                    if (count <= dayofWeek) {
                        num_day.setText("");
                    } else if (datesettter <= monthlen) {
                        num_day.setText("  " + (datesettter));

                        for (Event e: tmpCal) {
                            annoD = Integer.valueOf(ft.format(e.getDay()).substring(0,4));
                            meseD = Integer.valueOf(ft.format(e.getDay()).substring(5,7));
                            giornoD = Integer.valueOf(ft.format(e.getDay()).substring(8,10));

                            //bruttissimo if che controlla se la data è la seguente
                            //In futuro andra gestito con Date cosi basta solo un compare
                            if(anno == annoD){
                                if(meseD == mese){
                                    if(giornoD == datesettter){
                                        HBox hBoxDay = new HBox();
                                        hBoxDay.setSpacing(5);
                                        Label colore = new Label();
                                        colore.setPrefSize(10,10);
                                        colore.setStyle("-fx-background-color: "+e.getType().getColour()+";");
                                        Label impegno = new Label();
                                        Label orario = new Label();
                                        orario.setText(" "+ora.format(e.getStart())+" - "+ora.format(e.getEnd()));

                                        impegno.setText(e.getTitle());
                                        hBoxDay.getChildren().addAll(colore,impegno,orario);
                                        hBoxDay.setAlignment(Pos.CENTER);
                                        eventiVerticali.getChildren().add(hBoxDay);
                                    }
                                }
                            }
                        }

                        datesettter++;
                    } else {
                        num_day.setText("");
                    }
                }
                count++;
                r.setFill(Color.WHITE);
                r.setStyle("-fx-stroke: black; -fx-stroke-width: 2;");

                casella.setCenter(r);
                num_day.setAlignment(Pos.TOP_LEFT);

                casella_text.setTop(num_day);
                eventiVerticali.setAlignment(Pos.CENTER);
                casella_text.setCenter(eventiVerticali);
                StackPane cella = new StackPane(casella,casella_text);

                //da metter qua dentro la gestione migliore della data
                cella.setOnMouseClicked(mouseEvent -> System.out.println("PREMUTO"));

                calendar.add(cella, j, i);
            }
        }

        calendar.setAlignment(Pos.TOP_CENTER);
        return calendar;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
