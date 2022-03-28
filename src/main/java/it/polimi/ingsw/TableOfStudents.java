package it.polimi.ingsw;

import java.util.*;

public class TableOfStudents {
    public Color color;
    public Collection studentsInTable= HashSet<Student>;
    public int limitOfStudents;

    public TableOfStudents(Color color, Collection studentsInTable, int limitOfStudents) {
        this.color = color;
        this.studentsInTable = studentsInTable;
        this.limitOfStudents = limitOfStudents;
    }

    public Collection getStudentsInTable() {
        return studentsInTable;
    }

    public void setStudentsInTable(Collection studentsInTable) {
        this.studentsInTable = studentsInTable;
    }
}
