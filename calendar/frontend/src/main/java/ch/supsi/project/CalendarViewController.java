package ch.supsi.project;

import ch.supsi.project.applicationlayer.CalendarController;
import ch.supsi.project.model.Event;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CalendarViewController {
    private CalendarController calendario;

    public CalendarViewController(){
        calendario = new CalendarController("Data.csv","CSV");

    }

    public List<Event> getCalendarByMonth(Date date){
        //Calendar m1 = Calendar.getInstance(month);
        return calendario.getCalendar();
    }

    public Event addEvent(Event event){
        return calendario.addEvent(event);
    }
}
