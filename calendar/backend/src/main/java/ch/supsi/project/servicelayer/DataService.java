package ch.supsi.project.servicelayer;

import ch.supsi.project.datalayer.CsvDataAccess;
import ch.supsi.project.datalayer.DataAccess;
import ch.supsi.project.datalayer.JsonDataAccess;
import ch.supsi.project.model.Event;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class DataService {
    private List<Event> calendar;
    private DataAccess dataAccess;

    public DataService(){
        PreferencesService preferencesService = new PreferencesService();
        String inputFile = preferencesService.getPath();
        String type = preferencesService.getExtension();
        calendar = new ArrayList<>();

        if(preferencesService.isExist()){
            switch (type){
                case "CSV":
                    dataAccess = new CsvDataAccess(inputFile + "data" + ".csv");
                    break;
                case "JSON":
                    dataAccess = new JsonDataAccess(inputFile + "data" + ".json");
                    break;
                default:
                    System.out.println("File non riconosciuto");
                    throw new IllegalArgumentException("File non riconosciuto");
            }

            calendar = dataAccess.read();
        }
    }

    public Event addEvent(Event event){
        Event tmp = getEventByDayTime(event.getDay().getTime(),event.getStart().getTime(),event.getEnd().getTime());
        if(tmp == null){
            calendar.add(event);
            dataAccess.append(event);
            return event;
        }else{
            return null;
        }
    }

    public Event getEventByDayTime(long day, long start, long end){
        int i = 0;
        boolean find = false;
        Event event = null;

        while(!find && i< calendar.size()){
            Event tmp = calendar.get(i);
            if(tmp.getDay().getTime() == day && tmp.getStart().getTime() == start && tmp.getEnd().getTime() == end){
                event = tmp;
                find = true;
            }
            i++;
        }

        return event;
    }

    public List<Event> getEventsByMonth(Date date){
        List<Event> tmp;

        tmp = calendar.stream().filter(e -> date.getMonth() == e.getDay().getMonth()).collect(Collectors.toList());

        return tmp;
    }

    public List<Event> getCalendar(){
        List<Event> tmp = new ArrayList<>();
        tmp.addAll(calendar);

        return tmp;
    }
}
