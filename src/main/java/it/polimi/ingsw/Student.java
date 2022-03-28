package it.polimi.ingsw;

public class Student {
    private final Color color;

    public Student(Color color){
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public String toString() {
        return "Student: " + "color = " + color + "\n";
    }
}