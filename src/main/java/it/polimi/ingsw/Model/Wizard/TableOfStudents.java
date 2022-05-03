package it.polimi.ingsw.Model.Wizard;

import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.SchoolsMembers.Color;
import it.polimi.ingsw.Model.SchoolsMembers.Student;

import java.util.*;

/**
 * This class represents the Table of students placed just below the professors' seats and is iterated 5 times in the board, one for each color
 */
public class TableOfStudents {
    private final Color color;
    private final Collection<Student> studentsInTable= new HashSet<>();
    private final int limitOfStudents;

    /**
     * constructs the class
     * @param color one of five colors from the enum Color
     */
    public TableOfStudents(Color color) {
        this.color = color;
        limitOfStudents=10;
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
    public void setStudentsInTable(Student student) throws ExceptionGame{
        if(studentsInTable.size()<10)
            studentsInTable.add(student);
        else
            throw new ExceptionGame("Reached the limit of students in the table of color: "+ this.color);
    }
}
