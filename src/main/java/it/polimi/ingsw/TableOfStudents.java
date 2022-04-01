package it.polimi.ingsw;

import java.util.*;

/**
 * This class represents the Table of students placed just below the professors' seats and is iterated 5 times in the board, one for each color
 */
public class TableOfStudents {
    public Color color;
    public Collection<Student> studentsInTable= new HashSet<>();
    public int limitOfStudents;

    /**
     * constructs the class
     * @param color one of five colors from the enum Color
     */
    public TableOfStudents(Color color) {
        this.color = color;
        limitOfStudents=9;
        }

    /**
     * @return an HashSet of students placed in the table of their correspondent color
     */
    public Collection<Student> getStudentsInTable() {
        return studentsInTable;
    }

    /**
     * @return the color of the table
     */
    public Color getColor() {
        return color;
    }

    /**
     * @param student is the student that is going to be added to the Table
     */
    public void setStudentsInTable(Student student) {
        studentsInTable.add(student);
    }
}
