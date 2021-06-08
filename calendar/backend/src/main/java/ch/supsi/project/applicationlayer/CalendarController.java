package ch.supsi.project.applicationlayer;

import ch.supsi.project.model.Colour;
import ch.supsi.project.model.Event;
import ch.supsi.project.model.EventType;
import ch.supsi.project.model.Type;
import ch.supsi.project.servicelayer.DataService;
import ch.supsi.project.servicelayer.PreferencesService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CalendarController {
    private DataService dataService;
    public static final List<EventType> eventTypeList;

    static {
        eventTypeList = new ArrayList<>();

        eventTypeList.add(new EventType(Type.LECTION, Colour.BLUE));
        eventTypeList.add(new EventType(Type.LABORATORY, Colour.RED));
        eventTypeList.add(new EventType(Type.EXAM, Colour.GREEN));
        eventTypeList.add(new EventType(Type.HOLIDAY, Colour.ORANGE));
        eventTypeList.add(new EventType(Type.OTHERS, Colour.PURPLE));
    }

    public CalendarController(){
        dataService = new DataService();
    }

    public Event addEvent(Event event){
        if(dataService.addEvent(event) != null){
            System.out.println("Evento inserito");
            return event;
        }else{
            System.out.println("L'evento non può essere inserito, spazio occupato");
            return null;
        }
    }

    public List<Event> getCalendar(){
        return dataService.getCalendar();
    }

    public List<Event> getEventByMonth(Date date){
        return dataService.getEventsByMonth(date);
    }

    public static EventType getEventType(Type type){
        int i = 0;
        boolean find = false;
        EventType tmp = null;

        while(i < eventTypeList.size() && !find){
            tmp = eventTypeList.get(i);

            if(tmp.getDescription() == type){
                find = true;
            }

            i++;
        }

        if(find){
            return tmp;
        }else {
            return null;
        }
    }
}
