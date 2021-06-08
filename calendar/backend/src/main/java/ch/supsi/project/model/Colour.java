package ch.supsi.project.model;

public enum Colour {
    BLUE("#ADD8E6"), RED("#FF1919"), GREEN("#00FF00"), ORANGE("#FFA500"), PURPLE("#B161E0");
    private final String hexCode;

    Colour(String hexCode) {
        this.hexCode = hexCode;
    }

    public String getHexCode(){
        return this.hexCode;
    }

}
