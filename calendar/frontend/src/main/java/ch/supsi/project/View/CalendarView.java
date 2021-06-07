package ch.supsi.project.View;

import ch.supsi.project.Controller.CalendarViewController;
import ch.supsi.project.Utils.DateComparator;
import ch.supsi.project.model.Event;
import ch.supsi.project.servicelayer.PreferencesService;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

import java.text.DateFormatSymbols;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

import static java.util.Calendar.*;

public class CalendarView {
    static public Calendar dataOra = Calendar.getInstance();
    private ResourceBundle resourceBundle;
    private PreferencesService preferencesService;
    private CalendarViewController calendarViewController;
    private HBox moveMonthMenu;
    private BorderPane calendar;
    private Label monthCurrent;
    private New newView;

    public CalendarView(ResourceBundle resourceBundle){
        this.resourceBundle = resourceBundle;

        //calendarViewController = new CalendarViewController(preferencesService);

        //Creo bottoni << >>
        moveMonthMenu = new HBox();
        moveMonthMenu.setPadding(new Insets(5, 5, 5, 5));
        moveMonthMenu.setSpacing(10);

        Button monthPrev = new Button();
        Button monthNext = new Button();
        monthCurrent = new Label();

        monthCurrent.setText(getMeseStampare() + " " + dataOra.get(Calendar.YEAR));
        monthCurrent.setFont(new Font("Arial", 20));

        monthNext.setText(">>");
        monthPrev.setText("<<");

        moveMonthMenu.getChildren().addAll(monthPrev, monthCurrent, monthNext);
        moveMonthMenu.setAlignment(Pos.TOP_CENTER);

        calendar = new BorderPane();

        calendar.setCenter(updateCalendario());

        EventHandler<MouseEvent> clickNextMonth = mouseEvent -> {
            nextMonth();
        };

        EventHandler<MouseEvent> clickPrevMonth = mouseEvent -> {
            prevMonth();
        };

        monthNext.setOnMouseClicked(clickNextMonth);
        monthPrev.setOnMouseClicked(clickPrevMonth);
    }

    public void setPreferencesService(PreferencesService preferencesService) {
        this.preferencesService = preferencesService;

        calendarViewController = new CalendarViewController(preferencesService);
    }

    private GridPane updateCalendario() {
        GridPane calendarGrid = new GridPane();
        List<Event> tmpCal = calendarViewController.getCalendarByMonth(dataOra.getTime());
        tmpCal.sort(new DateComparator());

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
            daysOfTheWeek[i].setFont(new Font("Courier", 15));

            calendarGrid.add(daysOfTheWeek[i], i+1, 0);
        }

        boolean beforeMonth = true;
        boolean afterMonth = false;

        Calendar start = Calendar.getInstance();

        LocalDate date = LocalDate.of(dataOra.get(YEAR), dataOra.get(MONTH)+1, 1);

        start.set(YEAR, date.getYear());
        start.set(MONTH, date.getMonthValue()-1);

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
            List<Event> eventsOfToday = new ArrayList<>();
            for (Event e : tmpCal) {
                if(Cell.ft.format(e.getDay()).compareTo(Cell.ft.format(start.getTime())) == 0){
                    eventsOfToday.add(e);
                }
            }

            Calendar cellDate = Calendar.getInstance();
            cellDate.setTime(start.getTime());
            Cell c = new Cell(130, cellDate, eventsOfToday, resourceBundle,this);

            if(beforeMonth){
                c.isDayOfCurrMonth(false);
            } else c.isDayOfCurrMonth(!afterMonth);

            calendarGrid.add(c, i, j);

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

        calendarGrid.setAlignment(Pos.TOP_CENTER);
        return calendarGrid;
    }

    public void newEventModal(Calendar data) {
        newView = new New(resourceBundle,data,calendarViewController);
        newView.getModalStage().showAndWait();
        calendar.setCenter(updateCalendario());
    }

    public void nextMonth(){
        dataOra.add(MONTH,1);
        monthCurrent.setText(getMeseStampare()  + " " + dataOra.get(YEAR));
        calendar.setCenter(updateCalendario());
    }

    public void prevMonth(){
        dataOra.add(MONTH, -1);
        monthCurrent.setText(getMeseStampare() + " " + dataOra.get(YEAR));
        calendar.setCenter(updateCalendario());
    }

    public String getMeseStampare(){
        String mese = getMonthToInt(dataOra.get(Calendar.MONTH));
        return mese.substring(0,1).toUpperCase() + mese.substring(1);
        //return resourceBundle.getString(mese+".testo");
    }

    private String getMonthToInt(int num) {
        String month = "";
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();
        if (num >= 0 && num <= 11 ) {
            month = months[num];
        }
        return month.toLowerCase();
    }

    public HBox getMoveMonthMenu() {
        return moveMonthMenu;
    }

    public BorderPane getCalendar() {
        return calendar;
    }
}
