package it.polimi.ingsw.ModelTest.SchoolsLandsTest.SchoolsMembersTest;



import it.polimi.ingsw.Model.SchoolsMembers.Color;
import it.polimi.ingsw.Model.SchoolsMembers.Student;
import it.polimi.ingsw.Model.SchoolsMembers.StudentBag;
import org.junit.jupiter.api.*;
import org.junit.Test;

public class StudentBagTest {

    private final StudentBag studentBag = new StudentBag();

    @Test
    @DisplayName("Test Of StudentBag.drawStudent")
    public void emptyStudentBagTest(){
        int initialSize = studentBag.getStudentsInBag().size();
        for(int i=0; i<initialSize ; i++) {
            drawStudentTest();
            studentInBagAfter();
        }
    }

    @Test
    public void StudentBagIsEmpty_TestException(){
        Assertions.assertThrows(IllegalArgumentException.class, ()->{
            int initializeSize = studentBag.getStudentsInBag().size();
            for(int i = 0 ; i<initializeSize + 1; i++){
                     drawStudentTest();}} );
    }

    @Test
   public void drawStudentTest(){
        int sizeBefore = studentBag.getStudentsInBag().size();
        Student student = studentBag.drawStudent();
        Assertions.assertEquals(sizeBefore -1, studentBag.getStudentsInBag().size());
        Color c = student.getColor();
        Assertions.assertTrue(studentBag.getNumberOfStudentsInBag(c) < 26);
        Assertions.assertFalse(studentBag.getStudentsInBag().contains(student));
   }


    public void studentInBagAfter(){
        System.out.println(studentBag);
    }



}
