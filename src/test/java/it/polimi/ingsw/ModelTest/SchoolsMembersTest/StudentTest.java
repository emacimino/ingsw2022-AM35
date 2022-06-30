package it.polimi.ingsw.ModelTest.SchoolsMembersTest;

import it.polimi.ingsw.Model.SchoolsMembers.Color;
import it.polimi.ingsw.Model.SchoolsMembers.Student;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Class used to test the Student class
 */
class StudentTest {

    /**
     * This method tests the Student Class and fails if the student is not instanced correctly
     */
    @Test
    void studentTest() {
        for (Color c: Color.values()) {
            Student student = new Student(c);
            Assertions.assertEquals(c, student.getColor());
            Assertions.assertEquals(student.toString(), "Student: color = "+c);
        }
    }


}