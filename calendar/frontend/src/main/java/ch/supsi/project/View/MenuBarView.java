package ch.supsi.project.View;

import javafx.geometry.Insets;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.Calendar;
import java.util.ResourceBundle;

public class MenuBarView {
    private ResourceBundle resourceBundle;
    private HBox top;
    private MenuBar file;
    private MenuBar edit;
    private MenuBar help;
    private Exit exit;
    private About about;
    private Settings settings;
    private Stage principalStage;
    private CalendarView calendarView;

    public MenuBarView(ResourceBundle resourceBundle, Stage principalStage, CalendarView calendarView){
        this.resourceBundle = resourceBundle;
        this.principalStage = principalStage;
        this.calendarView = calendarView;

        exit = new Exit(resourceBundle);
        about = new About(resourceBundle);
        settings = new Settings(resourceBundle);
        calendarView.setPreferencesService(settings.getPreferencesService());

        if(!settings.isExist()){
            settings.getStage().showAndWait();

            if(!settings.isExist()){
                System.exit(1);
            }
        }

        top = new HBox();
        top.setPadding(new Insets(5, 5, 5, 5));
        top.setSpacing(10);
        top.setStyle("-fx-background-color: #A0A0A0;");

        //Menu tendina file -> exit
        file = new MenuBar();
        Menu menuFile = new Menu(resourceBundle.getString("menuFile.testo"));
        MenuItem menuExit = new MenuItem(resourceBundle.getString("menuExit.testo"));
        menuFile.getItems().add(menuExit);
        MenuItem menuPref = new MenuItem(resourceBundle.getString("menuPref.testo"));
        menuFile.getItems().add(menuPref);
        file.getMenus().add(menuFile);

        //Menu tendina edit -> new.. edit->previous edit->next
        edit = new MenuBar();
        Menu menuEdit = new Menu(resourceBundle.getString("menuEdit.testo"));
        MenuItem menuNew = new MenuItem(resourceBundle.getString("menuNew.testo"));
        MenuItem menuPrev = new MenuItem(resourceBundle.getString("menuPrev.testo"));
        MenuItem menuNext = new MenuItem(resourceBundle.getString("menuNext.testo"));

        menuEdit.getItems().addAll(menuNew, menuPrev, menuNext);
        edit.getMenus().add(menuEdit);

        //Menu tendina help -> about
        help = new MenuBar();
        Menu menuHelp = new Menu(resourceBundle.getString("menuHelp.testo"));
        MenuItem menuAbout = new MenuItem(resourceBundle.getString("menuAbout.testo"));
        menuHelp.getItems().add(menuAbout);
        help.getMenus().add(menuHelp);

        //aggiungo il menubar alla parte top del socio
        top.getChildren().addAll(file, edit, help);

        menuExit.setOnAction(mouse -> {
            exit.getExitStage().showAndWait();

            if(exit.isEnd()){
                principalStage.close();
            }
        });

        menuPref.setOnAction(mouse -> {
            settings.getStage().showAndWait();
        });

        //action event dei menu
        menuPrev.setOnAction(mouseEvent -> {
            calendarView.prevMonth();
        });

        //Sono gli action event dei menu
        menuNext.setOnAction(mouseEvent -> {
            calendarView.nextMonth();
        });

        menuNew.setOnAction(mouse -> {
            calendarView.newEventModal(Calendar.getInstance());
        });

        menuAbout.setOnAction(x -> {
            about.getAboutStage().showAndWait();
        });
    }

    public HBox getTop() {
        return top;
    }

}
