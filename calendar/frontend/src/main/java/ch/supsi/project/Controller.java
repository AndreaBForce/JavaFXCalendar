package ch.supsi.project;

import ch.supsi.project.service_layer.CalendarContainer;

public class Controller {
    CalendarContainer calendar;

    public Controller(String inputFile, String outputFile) {
        this.calendar = new CalendarContainer(inputFile,outputFile);
    }

    public Controller(String inputFile) {
        this.calendar = new CalendarContainer(inputFile);
    }
}
