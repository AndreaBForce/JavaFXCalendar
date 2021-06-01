package ch.supsi.project.datalayer;

import ch.supsi.project.model.Colour;
import ch.supsi.project.model.Event;
import ch.supsi.project.model.EventType;
import ch.supsi.project.model.Type;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class JsonDataAccess implements DataAccess {
    private final String input;
    private final String output;

    public JsonDataAccess(String input) {
        this.input = input;
        this.output = input;
    }

    public JsonDataAccess(String input, String output) {
        this.input = input;
        this.output = output;
    }

    @Override
    public List<Event> read() {
        List<Event> eventList = new ArrayList<>();

        JSONParser jsonParser = new JSONParser();

        try(FileReader reader = new FileReader(input)){
            Object obj = jsonParser.parse(reader);

            JSONArray jsonArray = (JSONArray) obj;

            for(Object item : jsonArray){
                JSONObject app = (JSONObject) item;
                JSONObject tmp = (JSONObject) app.get("event");

                String title = (String) tmp.get("title");
                long day = (long) tmp.get("day");
                long start = (long) tmp.get("start");
                long end = (long) tmp.get("end");
                int description = (int) tmp.get("description");
                int colour = (int) tmp.get("colour");

                Event event = new Event(title,day,start,end,new EventType(Type.values()[description], Colour.values()[colour]));
                eventList.add(event);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
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

        JSONObject jsonEventDetails = new JSONObject();
        jsonEventDetails.put("title",event.getTitle());
        jsonEventDetails.put("day",event.getDay().getTime());
        jsonEventDetails.put("start",event.getStart().getTime());
        jsonEventDetails.put("end",event.getEnd().getTime());
        jsonEventDetails.put("description",event.getType().getDescription().ordinal());
        jsonEventDetails.put("colour",event.getType().getColour().ordinal());

        JSONObject jsonEvent = new JSONObject();
        jsonEvent.put("event",jsonEventDetails);

        try (FileWriter file = new FileWriter(output,true)) {
            //We can write any JSONArray or JSONObject instance to the file
            file.write(jsonEvent.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

        /*try(FileWriter file = new FileWriter(output,true)) {
            String str = String.format("\"event\": {\n\t\"title\": \"%s\",\n\t\"day\": \"%d\",\n\t\"start\": \"%d\",\n\t\"end\": \"%d\",\n\t\"description\": \"%d\",\n\t\"colour\": \"%d\"\n}\n",
                    event.getTitle(), event.getDay().getTime(), event.getStart().getTime(), event.getEnd().getTime(), event.getType().getDescription().ordinal(), event.getType().getColour().ordinal());

            file.write(str);

        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    @Override
    public void write(List<Event> eventList) {
        for(Event e : eventList){
            append(e);
        }
    }
}
