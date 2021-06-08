package ch.supsi.project;

import ch.supsi.project.View.CalendarView;
import ch.supsi.project.View.MenuBarView;
import ch.supsi.project.datalayer.Preferences;
import ch.supsi.project.servicelayer.PreferencesService;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.*;

import static java.util.Calendar.*;

public class MainGUI extends Application {
    private ResourceBundle resourceBundle;
    private PreferencesService preferencesService;
    private MenuBarView menuBarView;
    private CalendarView calendarView;

    @Override
    public void start(Stage stage){
        Locale.setDefault(Locale.ENGLISH);
        resourceBundle = ResourceBundle.getBundle("i18n/stringhe");

        preferencesService = new PreferencesService();
        if(preferencesService.isExist()){
            switch (preferencesService.getLanguage()){
                case "EN":
                    Locale.setDefault(Locale.ENGLISH);
                    break;
                case "IT":
                    Locale.setDefault(Locale.ITALIAN);
                    break;
                case "DE":
                    Locale.setDefault(Locale.GERMAN);
                    break;
                case "FR":
                    Locale.setDefault(Locale.FRANCE);
                    break;

            }
            resourceBundle = ResourceBundle.getBundle("i18n/stringhe");
        }

        try {
            //Inizializzo border pane principale
            BorderPane root = new BorderPane();
            root.setPrefSize(900, 700);

            calendarView = new CalendarView(resourceBundle);

            menuBarView = new MenuBarView(resourceBundle,stage,calendarView);

            stage.setTitle(resourceBundle.getString("stageTitle.testo") + " (" + calendarView.getMeseStampare().charAt(0) + "" + calendarView.getMeseStampare().substring(1).toLowerCase() + " " + calendarView.dataOra.get(YEAR) + ")");

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

    public static void main(String[] args) {
        launch(args);

    }
}
