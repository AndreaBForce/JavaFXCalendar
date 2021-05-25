package ch.supsi.project.application_layer;

public enum Colour {
    BLUE("#ADD8E6"), RED("#FF0000"), GREEN("#00FF00"), ORANGE("#FFA500"), PURPLE("#800080");
    private final String hexCode;

    Colour(String hexCode) {
        this.hexCode = hexCode;
    }

    public String getHexCode(){
        return this.hexCode;
    }
}
