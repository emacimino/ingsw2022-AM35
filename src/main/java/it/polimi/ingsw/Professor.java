package it.polimi.ingsw;

public class Professor {
    private Color color;
    private Wizard property;

    public Professor(Color color, Wizard property) {
        this.color = color;
        this.property = property;
    }

    public void setProperty(Wizard property) {
        this.property = property;
    }

    public static Color getColor() {
        return color;
    }

    public Wizard getProperty() {
        return property;
    }
}
