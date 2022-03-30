package it.polimi.ingsw;

import java.util.*;

public class TableOfStudents {
    public Color color;
    public Collection<Student> studentsInTable= new HashSet<>();
    public int limitOfStudents;

    public TableOfStudents(Color color) {
        this.color = color;
        limitOfStudents=9;
        }

    public Collection getStudentsInTable() {
        return studentsInTable;
    }

    public Color getColor() {
        return color;
    }

    public void setStudentsInTable(Collection studentsInTable) {
        this.studentsInTable = studentsInTable;
    }
}
