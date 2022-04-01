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
        Board b1 = new Board(7);
        for (Color c:
             Color.values()) {
            assertNotNull(b1.getTables());
        }
    }

/*    @Test
    void fillBoardWithStudents(){
        Board b1 = new Board(7);
        for (Color c:
                Color.values()) {
            Student student = new Student(c);
            b1.addStudentInTable(student);
            assertTrue(b1.getTables().stream().anyMatch( (TableOfStudents t) -> {t.getColor().equals(c);t.getStudentsInTable().contains(student);return true;}));
        }
    }
*/
}
