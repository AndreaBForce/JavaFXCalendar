package ch.supsi.project.service_layer;

import java.util.ArrayList;
import java.util.List;

public class CalendarContainer {
    private List<Event> calendar;

    public CalendarContainer(){
        calendar = new ArrayList<>();
    }

    public void addEvent(Event event){
        calendar.add(event);
    }

    public List<Event> getCalendar(){
        List<Event> tmp = new ArrayList<>();
        tmp.addAll(calendar);

        return tmp;
    }
}
