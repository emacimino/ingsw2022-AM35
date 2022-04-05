package it.polimi.ingsw.ModelTest.WizardTest;
import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.SchoolsMembers.Color;
import it.polimi.ingsw.Model.SchoolsMembers.Professor;
import it.polimi.ingsw.Model.SchoolsMembers.Student;
import it.polimi.ingsw.Model.Wizard.Board;
import it.polimi.ingsw.Model.Wizard.TableOfStudents;
import org.junit.jupiter.api.Assertions;
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
            Assertions.assertDoesNotThrow(()-> {
                b1.addStudentInTable(stud);
                TableOfStudents table = b1.getTables().stream().filter(t -> t.getColor().equals(c)).findAny().get();
                Assertions.assertTrue(table.getStudentsInTable().contains(stud));
                b1.setProfessorInTable(p1);
                Assertions.assertTrue(b1.getProfessorInTable().contains(p1));
            });
        }
    }

    @ParameterizedTest
    @EnumSource(Color.class)
    void isProfessorPresent_Test(Color c){
        Board board = new Board();
        Professor professor = new Professor(c);
        board.setProfessorInTable(professor);
        Assertions.assertTrue(board.isProfessorPresent(c));
    }

    @ParameterizedTest
    @EnumSource(Color.class)
    void getStudentFromTable_Test(Color c){
        Board board = new Board();
        Student student = new Student(c);
        Assertions.assertDoesNotThrow(()-> {
            board.addStudentInTable(student);
            Assertions.assertEquals(1, board.getStudentsFromTable(c).size());
            for(Color color : Color.values())
                if(!color.equals(c))
                    Assertions.assertEquals(0, board.getStudentsFromTable(color).size());
        });

    }

    @ParameterizedTest
    @EnumSource(Color.class)
    void removeProfessorFromTable_Test(Color c){
        Board board = new Board();
        Professor professor = new Professor(c);
        board.setProfessorInTable(professor);
        Assertions.assertTrue(board.getProfessorInTable().contains(professor));
        Assertions.assertDoesNotThrow(()-> {
            Professor p = board.removeProfessorFromTable(c);
        });

    }

    @ParameterizedTest
    @EnumSource(Color.class)
    void addStudentInTable_Test(Color c){
        Board board = new Board();
        Student student = new Student(c);
        Assertions.assertDoesNotThrow(()-> {
            board.addStudentInTable(student);
            Assertions.assertTrue(board.getStudentsFromTable(c).contains(student));
        });
    }
}
