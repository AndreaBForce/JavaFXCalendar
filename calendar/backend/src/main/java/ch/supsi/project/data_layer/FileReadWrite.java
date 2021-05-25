package ch.supsi.project.data_layer;

import ch.supsi.project.application_layer.Colour;
import ch.supsi.project.service_layer.Type;
import ch.supsi.project.service_layer.Event;
import ch.supsi.project.service_layer.EventType;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public final class FileReadWrite {
    private final String input;
    private final String output;

    public FileReadWrite(String input) {
        this.input = input;
        this.output = input;
    }

    public FileReadWrite(String input, String output) {
        this.input = input;
        this.output = output;
    }

    public List<Event> read(){
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

    public void append(Event event){
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

    public void write(List<Event> eventList){
        File tmpFile = new File(output);
        try {
            if(tmpFile.createNewFile()){
                System.out.println("File creato");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        try(FileWriter file = new FileWriter(output)) {

            for(Event e : eventList){
                String str = String.format("%s,%d,%d,%d,%d,%d\n", e.getTitle(), e.getDay().getTime(), e.getStart().getTime(), e.getEnd().getTime(), e.getType().getDescription().ordinal(), e.getType().getColour().ordinal());
                file.write(str);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
