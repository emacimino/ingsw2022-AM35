package it.polimi.ingsw.Wizard;


import it.polimi.ingsw.Charachter.Color;
import it.polimi.ingsw.Charachter.Professor;
import it.polimi.ingsw.Charachter.Student;

import java.util.Collection;
import java.util.HashSet;

/**
 * Board is the board you are given in the beginning and belongs only to you
 */
public class Board {
    private final Collection<Tower> towersInBoard= new HashSet<>();
    public final Collection<Professor> professorInTable= new HashSet<>();
    private final Collection<TableOfStudents> tables= new HashSet<>();
    private final Collection<Student> studentsInEntrance= new HashSet<>();

    /**
     * constructs the class
     */
    public Board() {
        for (Color c: Color.values()) {
            tables.add(new TableOfStudents(c));
        }
    }

    /**
     * @return an HashSet of towers that are on the board
     */
    public Collection<Tower> getTowersInBoard() {
        return towersInBoard;
    }

    /**
     * @return an HashSet of professors in table
     */
    public Collection<Professor> getProfessorInTable() {
        return professorInTable;
    }

    /**
     * @return an HashSet of tables of students
     */
    public Collection<TableOfStudents> getTables() {
        return tables;
    }

    /**
     * @return an HashSet of students sitting in the entrance
     */
    public Collection<Student> getStudentsInEntrance() {
        return studentsInEntrance;
    }

    /**
     * @param professor adds a professor to the HashSet
     */
    public void setProfessorInTable(Professor professor) {
        professorInTable.add(professor);
    }

    /**
     * collects coins every 3 students placed in the table of students
     */
    public void collectCoins(){}

    /**
     * This method is used to place a student on the table of students
     * @param stud is the student that's going to be added to the collection
     */
    public void addStudentInTable(Student stud){
        Color c=stud.getColor();
        for (TableOfStudents t:
             tables) {
            if(t.getColor().equals(c))t.setStudentsInTable(stud);
        }
    }
}
