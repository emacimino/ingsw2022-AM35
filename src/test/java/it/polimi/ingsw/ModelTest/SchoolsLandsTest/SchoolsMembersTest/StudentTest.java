package it.polimi.ingsw.ModelTest.SchoolsLandsTest.SchoolsMembersTest;

import it.polimi.ingsw.Model.SchoolsMembers.Color;
import it.polimi.ingsw.Model.SchoolsMembers.Student;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class StudentTest {

    @Test
    void studentTest() {
        for (Color c: Color.values()) {
            Student student = new Student(c);
            Assertions.assertEquals(c, student.getColor());
            Assertions.assertEquals(student.toString(), "Student: color = "+c+ "\n");
        }
    }


}