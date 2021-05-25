package ch.supsi.project.service_layer;

import ch.supsi.project.application_layer.Colour;
import ch.supsi.project.data_layer.FileReadWrite;

import java.util.ArrayList;
import java.util.List;

public class CalendarContainer {
    private List<Event> calendar;
    private FileReadWrite fileReadWrite;
    public static final List<EventType> eventTypeList;

    static {
        eventTypeList = new ArrayList<>();

        eventTypeList.add(new EventType(Type.LECTION, Colour.BLUE));
        eventTypeList.add(new EventType(Type.LABORATORY, Colour.RED));
        eventTypeList.add(new EventType(Type.EXAM, Colour.GREEN));
        eventTypeList.add(new EventType(Type.HOLIDAY, Colour.ORANGE));
        eventTypeList.add(new EventType(Type.OTHERS, Colour.PURPLE));
    }

    public CalendarContainer(String inputFile){
        fileReadWrite = new FileReadWrite(inputFile);

        calendar = fileReadWrite.read();
    }

    public CalendarContainer(String inputFile, String outputFile){
        fileReadWrite = new FileReadWrite(inputFile,outputFile);

        calendar = fileReadWrite.read();
    }

    public void addEvent(Event event){
        calendar.add(event);
        calendar.sort(new DateComparator());
        fileReadWrite.append(event);
    }

    public List<Event> getCalendar(){
        List<Event> tmp = new ArrayList<>();
        tmp.addAll(calendar);

        return tmp;
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

    public void close(){
        fileReadWrite.write(calendar);
    }
}
