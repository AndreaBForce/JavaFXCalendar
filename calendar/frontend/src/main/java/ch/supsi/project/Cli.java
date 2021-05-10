package ch.supsi.project;

import ch.supsi.project.application_layer.Colour;
import ch.supsi.project.service_layer.Type;
import ch.supsi.project.service_layer.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Cli {
    public static void main(String[] args) {
        CalendarContainer calendarContainer = new CalendarContainer("Prova.txt");
        List<EventType> eventTypeList = new ArrayList<>();

        eventTypeList.add(new EventType(Type.LECTION, Colour.BLUE));
        eventTypeList.add(new EventType(Type.LABORATORY, Colour.RED));
        eventTypeList.add(new EventType(Type.EXAM, Colour.GREEN));
        eventTypeList.add(new EventType(Type.HOLIDAY, Colour.ORANGE));
        eventTypeList.add(new EventType(Type.OTHERS, Colour.PURPLE));

        Date time = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        cal.add(Calendar.HOUR_OF_DAY,3);
        long start = cal.getTime().getTime();
        cal.add(Calendar.HOUR_OF_DAY, 3);
        long end = cal.getTime().getTime();

        /*calendarContainer.addEvent(new Event("1",time.getTime(),start,end, eventTypeList.get(0)));
        calendarContainer.addEvent(new Event("2",time.getTime(),start,end, eventTypeList.get(1)));
        calendarContainer.addEvent(new Event("3",time.getTime(),start,end, eventTypeList.get(2)));
        calendarContainer.addEvent(new Event("4",time.getTime(),start,end, eventTypeList.get(3)));
        calendarContainer.addEvent(new Event("5",time.getTime(),start,end, eventTypeList.get(4)));*/

        List<Event> list = calendarContainer.getCalendar();

        list.forEach(System.out::println);

        calendarContainer.close();
    }
}
