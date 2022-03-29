package it.polimi.ingsw;

import sun.tools.jconsole.Tab;

import java.util.Collection;
import java.util.HashSet;

public class Board {
    private Collection towersInBoard= new HashSet<Tower>();
    private Collection professorInTable= new HashSet<Professor>();
    private Collection tables= new HashSet<TableOfStudents>();
    private Collection studentsInEntrance= new HashSet<Student>();
    private final int limitStudentInEntrance;

    public Board(int limitStudentInEntrance) {
        this.limitStudentInEntrance = limitStudentInEntrance;
        for (Color c: Color.values()) {
            tables.add(new TableOfStudents(c));
        }
    }

    public Collection getTowersInBoard() {
        return towersInBoard;
    }

    public Collection getProfessorInTable() {
        return professorInTable;
    }

    public Collection getTables() {
        return tables;
    }

    public Collection getStudentsInEntrance() {
        return studentsInEntrance;
    }

    public int getLimitStudentInEntrance() {
        return limitStudentInEntrance;
    }

    public void setProfessorInTable(Collection professorInTable) {
        this.professorInTable = professorInTable;
    }

    public void collectCoins(){}

    public void addStudentInTable(Student stud){
        Color c=stud.getColor();
        for (TableOfStudents t : tables ) {
            if(t.getColor() == c)
                t.studentsInTable.add(stud);
        }
    }
}
