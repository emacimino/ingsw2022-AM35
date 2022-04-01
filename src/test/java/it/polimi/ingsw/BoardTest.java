package it.polimi.ingsw;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {
    @Test
    void CreateBoard(){
        Board b1 = new Board();
        for (Color c:
             Color.values()) {
            assertNotNull(b1.getTables());
        }}
     @Test
    void CreateBoardAndFillTables(){
        Board b1 = new Board();
        for (Color c: Color.values()) {
            Student stud = new Student(c);
            Professor p1= new Professor(c);
            b1.addStudentInTable(stud);
            TableOfStudents table = b1.getTables().stream().filter(t -> t.getColor().equals(c)).findAny().get();
            Assertions.assertTrue(table.getStudentsInTable().contains(stud));
           b1.setProfessorInTable(p1);
           Assertions.assertTrue(b1.getProfessorInTable().contains(p1));
        }
    }
}
