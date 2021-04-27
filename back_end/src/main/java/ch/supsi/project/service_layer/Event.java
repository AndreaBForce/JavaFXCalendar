package ch.supsi.project.service_layer;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class Event {
    private String title;
    private Date day;
    private Date start;
    private Date end;
    private EventType type;

    public Event(String title, long day, long start, long end, EventType type) {
        this.title = title;
        this.day = new Date(day);
        this.start = new Date(start);
        this.end = new Date(end);
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public Date getDay() {
        return new Date(day.getTime());
    }

    public Date getStart() {
        return new Date(start.getTime());
    }

    public Date getEnd() {
        return new Date(end.getTime());
    }

    public EventType getType() {
        return type;
    }

    @Override
    public String toString() {
        SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat format2 = new SimpleDateFormat("HH:mm:ss");
        return "Event: " + title + ", " + format1.format(day) + ", " + format2.format(start) + ", " + format2.format(end) + ", " + type;
    }
}
