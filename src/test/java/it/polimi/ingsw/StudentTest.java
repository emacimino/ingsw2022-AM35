package it.polimi.ingsw;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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