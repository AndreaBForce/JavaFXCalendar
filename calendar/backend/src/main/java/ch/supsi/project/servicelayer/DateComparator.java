package ch.supsi.project.servicelayer;

import ch.supsi.project.model.Event;

import java.util.Comparator;

public class DateComparator implements Comparator<Event> {
    @Override
    public int compare(Event o1, Event o2) {
        if(o1.getDay().compareTo(o2.getDay()) != 0){
            return (int) (o1.getDay().getTime() - o2.getDay().getTime());
        }else{
            return (int) (o1.getStart().getTime() - o2.getStart().getTime());
        }
    }
}
