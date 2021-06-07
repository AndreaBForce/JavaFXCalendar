package ch.supsi.project.Controller;

import ch.supsi.project.applicationlayer.CalendarController;
import ch.supsi.project.model.Event;
import ch.supsi.project.servicelayer.PreferencesService;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CalendarViewController {
    private CalendarController calendario;

    public CalendarViewController(){
        calendario = new CalendarController();

    }

    public List<Event> getCalendarByMonth(Date date){
        return calendario.getEventByMonth(date);
    }

    public Event addEvent(Event event){
        return calendario.addEvent(event);
    }
}
