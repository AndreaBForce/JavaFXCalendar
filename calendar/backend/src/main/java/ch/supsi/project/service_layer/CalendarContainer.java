package ch.supsi.project.service_layer;

import ch.supsi.project.data_layer.FileReadWrite;

import java.util.ArrayList;
import java.util.List;

public class CalendarContainer {
    private List<Event> calendar;
    private FileReadWrite fileReadWrite;

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
    }

    public List<Event> getCalendar(){
        List<Event> tmp = new ArrayList<>();
        tmp.addAll(calendar);

        return tmp;
    }

    public void close(){
        fileReadWrite.write(calendar);
    }
}
