package it.polimi.ingsw;


import java.util.Collection;
import java.util.HashSet;

public class Board {
    private final Collection<Tower> towersInBoard= new HashSet<>();
    private final Collection<Professor> professorInTable= new HashSet<>();
    private final Collection<TableOfStudents> tables= new HashSet<>();
    private final Collection<Student> studentsInEntrance= new HashSet<>();

    public Board() {
        for (Color c: Color.values()) {
            tables.add(new TableOfStudents(c));
        }
    }

    public Collection<Tower> getTowersInBoard() {
        return towersInBoard;
    }

    public Collection<Professor> getProfessorInTable() {
        return professorInTable;
    }

    public Collection<TableOfStudents> getTables() {
        return tables;
    }

    public Collection<Student> getStudentsInEntrance() {
        return studentsInEntrance;
    }


    public void setProfessorInTable(Professor prof) {
        professorInTable.add(prof);
    }


    public void collectCoins(){

    }

    public void addStudentInTable(Student stud){
        Color c=stud.getColor();
        for (TableOfStudents t : tables ) {
            if(t.getColor() == c)
                t.studentsInTable.add(stud);
        }
    }
}
