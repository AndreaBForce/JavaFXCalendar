package ch.supsi.project;

import ch.supsi.project.applicationlayer.CalendarController;
import ch.supsi.project.model.EventType;
import javafx.application.Application;
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

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

import static java.util.Calendar.*;

public class MainGUI extends Application {
    private ResourceBundle resourceBundle;
    private MenuBarView menuBarView;
    private CalendarView calendarView;

    @Override
    public void start(Stage stage){
        Locale.setDefault(Locale.ENGLISH);
        resourceBundle = ResourceBundle.getBundle("i18n/stringhe");

        try {
            //Inizializzo border pane principale
            BorderPane root = new BorderPane();
            root.setPrefSize(900, 700);

            calendarView = new CalendarView(resourceBundle);

            menuBarView = new MenuBarView(resourceBundle,stage,calendarView);

            stage.setTitle(resourceBundle.getString("stageTitle.testo") + " (" + calendarView.getMeseTradotto().charAt(0) + "" + calendarView.getMeseTradotto().substring(1).toLowerCase() + " " + CalendarView.dataOra.get(YEAR) + ")");

            root.setTop(menuBarView.getTop());
            root.setCenter(calendarView.getMoveMonthMenu());
            root.setBottom(calendarView.getCalendar());

            //mostro la finestra principale
            Scene scene = new Scene(root, 940, 900);
            stage.setScene(scene);

            stage.setMinHeight(900);
            stage.setMinWidth(940);

            stage.setMaxHeight(980);
            stage.setMaxWidth(980);

            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static String printCalendar(Calendar c) {
        return Cell.ft.format(c.getTime());
    }

    public static void main(String[] args) {
        launch(args);

    }
}
