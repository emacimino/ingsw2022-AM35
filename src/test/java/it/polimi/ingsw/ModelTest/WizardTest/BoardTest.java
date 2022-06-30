package it.polimi.ingsw.ModelTest.WizardTest;
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

/**
 * Class used to test the board
 */
public class BoardTest {
    /**
     * Method that creates a board and tests if it is correct
     */
    @Test
    void CreateBoard(){
        Board b1 = new Board();
        for (Color c:
             Color.values()) {
            assertNotNull(b1.getTables());
        }}

    /**
     * Method that creates a board and tests if the tableOfStudents work
     */
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

    /**
     * Method used to verify the presence of the professor
     * @param c color
     */
    @ParameterizedTest
    @EnumSource(Color.class)
    void isProfessorPresent_Test(Color c){
        Board board = new Board();
        Professor professor = new Professor(c);
        board.setProfessorInTable(professor);
        Assertions.assertTrue(board.isProfessorPresent(c));
    }

    /**
     * Method used to test the get method that returns the students from the tables
     * @param c color
     */
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

    /**
     * Method used to test the removal of a professor and its consequence
     * @param c color
     */
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

    /**
     * Method used to test what the addition of students into a table provokes
     * @param c color
     */
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
