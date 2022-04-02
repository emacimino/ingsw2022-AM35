package it.polimi.ingsw.ModelTest.WizardTest;

import it.polimi.ingsw.Model.SchoolsMembers.Color;
import it.polimi.ingsw.Model.SchoolsMembers.Student;
import it.polimi.ingsw.Model.Wizard.TableOfStudents;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TableOfStudentsTest {
    @Test
    void IfTableExistsAndHas9StudentsSeatsItIsTrue(){
        for (Color c:
             Color.values()) {
            Student stud = new Student(c);
            TableOfStudents tos= new TableOfStudents(c);
            Assertions.assertEquals(c, tos.getColor());
            tos.studentsInTable.add(stud);
            Assertions.assertTrue(tos.studentsInTable.contains(stud));
        }
    }
    @Test
    void TableLoadingandReturn(){
        for (Color c:
             Color.values()) {
            TableOfStudents tos = new TableOfStudents(c);
            for (int i = 0; i < 9; i++) {
                Student s = new Student(c);
                tos.setStudentsInTable(s);
            }
            assertEquals(9, tos.getStudentsInTable().size());
        }
    }
}