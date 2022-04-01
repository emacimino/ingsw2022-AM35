package it.polimi.ingsw.ModelTest.SchoolsLandsTest.WizardTest;
import it.polimi.ingsw.Model.SchoolsMembers.Color;
import it.polimi.ingsw.Model.SchoolsMembers.Professor;
import it.polimi.ingsw.Model.SchoolsMembers.Student;
import it.polimi.ingsw.Model.Wizard.Board;
import it.polimi.ingsw.Model.Wizard.TableOfStudents;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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
