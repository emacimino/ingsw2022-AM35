package it.polimi.ingsw;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static it.polimi.ingsw.Professor.getColor;
import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {
    @Test
    void CreateBoardAndFillTables(){
        Board b1 = new Board(7);
        for (Color c: Color.values()) {
            Student stud = new Student(c);
            Professor p1= new Professor(c);
            b1.addStudentInTable(stud);
            Assertions.assertTrue(b1.getTables().contains(stud));
            b1.setProfessorInTable(p1);
            Assertions.assertTrue(b1.getProfessorInTable().contains(p1));
        }
    }
}
