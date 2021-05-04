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

import java.sql.Date;
import java.sql.SQLOutput;
import java.time.DayOfWeek;
import java.time.LocalDate;


public class Gui extends Application {
    static LocalDate dataOra = LocalDate.now();
    @Override
    public void start(Stage stage) throws Exception {

        /*
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
        */


        //PARTE FX GUI NUOVA VANNO IMPLEMENTATI I LISTENER
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
            lun.setFont(new Font("Arial", 12));

            Label dom = new Label();
            dom.setText("Sunday");
            dom.setFont(new Font("Arial", 12));

            Label mart = new Label();
            mart.setText("Tuesday");
            mart.setFont(new Font("Arial", 12));

            Label merc = new Label();
            merc.setText("Wednesday");
            merc.setFont(new Font("Arial", 12));

            Label giov = new Label();
            giov.setText("Thursday");
            giov.setFont(new Font("Arial", 12));

            Label vene = new Label();
            vene.setText("Friday");
            vene.setFont(new Font("Arial", 12));

            Label sab = new Label();
            sab.setText("Saturday");
            sab.setFont(new Font("Arial", 12));

            giorni.setAlignment(Pos.CENTER);
            giorni.setSpacing(50);
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
                    setupCalendario.setCenter(updateCalendario());
                }
            };

            EventHandler<MouseEvent> clickPrevMonth = new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    dataOra = dataOra.minusMonths(1);
                    monthCurrent.setText(dataOra.getMonth().toString() + " " + dataOra.getYear());
                    setupCalendario.setCenter(updateCalendario());
                }
            };

            monthNext.setOnMouseClicked(clickNextMonth);
            monthPrev.setOnMouseClicked(clickPrevMonth);

            //action event dei menu
            menuPrev.setOnAction(mouseEvent -> {
                dataOra = dataOra.minusMonths(1);
                monthCurrent.setText(dataOra.getMonth().toString() + " " + dataOra.getYear());
                setupCalendario.setCenter(updateCalendario());
            });

            //Sono gli action event dei menu
            menuNext.setOnAction(mouseEvent -> {
                dataOra = dataOra.plusMonths(1);
                monthCurrent.setText(dataOra.getMonth().toString() + " " + dataOra.getYear());
                setupCalendario.setCenter(updateCalendario());
            });

            //Qua avviene la creazione del calendario
            setupCalendario.setCenter(updateCalendario());

            root.setCenter(centerMen);
            root.setBottom(setupCalendario);

            Scene scene = new Scene(root, 720, 700);
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
            Scene aboutScene = new Scene(borderAbout,400,100);

            menuAbout.setOnAction(x -> {
                aboutStage.setScene(aboutScene);
                aboutStage.show();
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public GridPane updateCalendario() {
        GridPane calendar = new GridPane();

        double s = 100; // side of rectangle
        LocalDate start = dataOra.withDayOfMonth(1);
        int dayofWeek = start.getDayOfWeek().getValue();
        int monthlen = start.lengthOfMonth();
        int count = 1, datesettter = 1;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                BorderPane casella = new BorderPane();
                BorderPane casella_text = new BorderPane();
                Label num_day = new Label();

                Rectangle r = new Rectangle(s, s, s, s);

                if(dayofWeek == 7){
                    if (datesettter <= monthlen) {
                        num_day.setText("  " + (datesettter));
                        datesettter++;
                    }
                }else {
                    if (count <= dayofWeek) {
                        num_day.setText("");
                    } else if (datesettter <= monthlen) {
                        num_day.setText("  " + (datesettter));
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
                StackPane cella = new StackPane(casella, casella_text);

                if(!num_day.getText().equals("")){
                    final LocalDate date = start.plusDays(datesettter-2);
                    //da metter qua dentro la gestione migliore della data
                    cella.setOnMouseClicked(mouseEvent -> {
                        Stage modalStage = new Stage();
                        modalStage.setAlwaysOnTop(true);

                        GridPane modal = new GridPane();
                        modal.setHgap(10);
                        modal.setVgap(10);
                        modal.setPadding(new Insets(10, 10, 10, 10));
                        modal.setGridLinesVisible(true);

                        for (int k = 0; k < 2; k++) {
                            ColumnConstraints colConst = new ColumnConstraints();
                            colConst.setPercentWidth(100.0 / 2);
                            modal.getColumnConstraints().add(colConst);
                        }
                        for (int k = 0; k < 3; k++) {
                            RowConstraints rowConst = new RowConstraints();
                            rowConst.setPercentHeight(100.0 / 3);
                            modal.getRowConstraints().add(rowConst);
                        }

                        modalStage.setScene(new Scene(modal, 300, 400));
                        modalStage.setTitle(date.toString());
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
                }

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
