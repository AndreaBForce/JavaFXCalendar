package ch.supsi.project.datalayer;

import ch.supsi.project.model.Event;

import java.util.List;

public interface DataAccess {
    List<Event> read();
    void append(Event event);
    void write(List<Event> eventList);
}
