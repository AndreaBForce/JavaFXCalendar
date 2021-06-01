package ch.supsi.project.datalayer;

import ch.supsi.project.model.Colour;
import ch.supsi.project.model.Event;
import ch.supsi.project.model.EventType;
import ch.supsi.project.model.Type;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CsvDataAccess implements DataAccess {
    private final String input;
    private final String output;

    public CsvDataAccess(String input) {
        this.input = input;
        this.output = input;
    }

    public CsvDataAccess(String input, String output) {
        this.input = input;
        this.output = output;
    }

    @Override
    public List<Event> read() {
        List<Event> eventList = new ArrayList<>();

        try {
            File file = new File(input);
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String tmp = scanner.nextLine();
                String data[] = tmp.split(",");

                Event event = new Event(data[0],Long.parseLong(data[1]),Long.parseLong(data[2]),Long.parseLong(data[3]),new EventType(Type.values()[Integer.valueOf(data[4])], Colour.values()[Integer.valueOf(data[5])]));
                eventList.add(event);
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return eventList;
    }

    @Override
    public void append(Event event) {
        File tmpFile = new File(output);
        try {
            if(tmpFile.createNewFile()){
                System.out.println("File creato");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        try(FileWriter file = new FileWriter(output,true)) {

            String str = String.format("%s,%d,%d,%d,%d,%d\n", event.getTitle(), event.getDay().getTime(), event.getStart().getTime(), event.getEnd().getTime(), event.getType().getDescription().ordinal(), event.getType().getColour().ordinal());
            file.write(str);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void write(List<Event> eventList) {
        for(Event e : eventList){
            append(e);
        }
    }
}
