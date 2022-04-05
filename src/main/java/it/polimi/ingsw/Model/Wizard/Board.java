package it.polimi.ingsw.Model.Wizard;


import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.SchoolsMembers.Color;
import it.polimi.ingsw.Model.SchoolsMembers.Professor;
import it.polimi.ingsw.Model.SchoolsMembers.Student;

import java.util.Collection;
import java.util.EmptyStackException;
import java.util.HashSet;

/**
 * Board is the board you are given in the beginning and belongs only to you
 */
public class Board {
    private final Collection<Tower> towersInBoard= new HashSet<>();
    private final Collection<Professor> professorInTable= new HashSet<>();
    private final Collection<TableOfStudents> tables= new HashSet<>();
    private final Collection<Student> studentsInEntrance= new HashSet<>();
    private final int limitStudentOnTable = 10;

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
     * This method returns true if the professor of color passed as parameter is present in tha board
     * @param c is the color
     * @return true if the professor of color c is present in the board
     */
    public boolean isProfessorPresent(Color c)                                                                                                    {
        for(Professor p : professorInTable)
            if( p.getColor().equals(c))
                return true;
        return false;
    }

    /**
     * @return an HashSet of tables of students
     */
    public Collection<TableOfStudents> getTables() {
        return tables;
    }

    /**
     * This method returns the table of Student of the color passed as parameter, if there is no correspondence
     * between the color passed and the tables of students of the board, the method throws an exception
     * @param c is the color
     * @return the collection of students in the table of the parameter color
     * @throws ExceptionGame is thrown when there is no correspondence between the color and the tables of the board
     */
    public Collection<Student> getStudentsFromTable(Color c)throws ExceptionGame{
        for(TableOfStudents t : tables){
            if(t.getColor().equals(c))
                return t.getStudentsInTable();
        }
        throw new ExceptionGame("Can't find a Table of Students with assigned color:" + c);

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
     * This method remove the professor of color passed as parameter if present, otherwise it returns an exception
     * @param c is the color
     * @return the professor just removed
     * @throws ExceptionGame is thrown if the professor is not present
     */
    public Professor removeProfessorFromTable( Color c) throws ExceptionGame{
        for(Professor p : professorInTable)
            if( p.getColor().equals(c)) {
                professorInTable.remove(p);
                return p;
            }
        throw new ExceptionGame("This board does not contain the professor");
    }


    /**
     * This method is used to place a student on the table of students
     * @param stud is the student that's going to be added to the collection
     */
    public void addStudentInTable(Student stud) throws ExceptionGame{
        Color c=stud.getColor();
        for (TableOfStudents t: tables) {
            if(t.getColor().equals(c))
                t.setStudentsInTable(stud);
        }
    }
}
