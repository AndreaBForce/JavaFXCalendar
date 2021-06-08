package ch.supsi.project.model;

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
        return "" + description;
    }
}
