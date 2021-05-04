package ch.supsi.project.service_layer;

import ch.supsi.project.application_layer.Colour;
import ch.supsi.project.application_layer.Type;

public class EventType {
    private final Type description;
    private final Colour colour;

    public EventType(Type description, Colour colour) {
        this.description = description;
        this.colour = colour;
    }

    public Colour getColour() {
        return colour;
    }

    public Type getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "" + description + ", " + colour;
    }
}
