package it.polimi.ingsw;


import java.util.Collection;
import java.util.HashSet;

public class Board {
    private Collection<Tower> towersInBoard= new HashSet<>();
    private Collection<Professor> professorInTable= new HashSet<>();
    private Collection<TableOfStudents> tables= new HashSet<>();
    private Collection<Student> studentsInEntrance= new HashSet<>();
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
