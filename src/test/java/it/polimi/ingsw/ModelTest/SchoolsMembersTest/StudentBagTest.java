package it.polimi.ingsw.ModelTest.SchoolsMembersTest;



import it.polimi.ingsw.Model.SchoolsMembers.Color;
import it.polimi.ingsw.Model.SchoolsMembers.Student;
import it.polimi.ingsw.Model.SchoolsMembers.StudentBag;
import org.junit.jupiter.api.*;

/**
 * Class used to test the students' bag
 */
public class StudentBagTest {

    private final StudentBag studentBag = new StudentBag();

    /**
     * This method empties the studentBag
     */
    @Test
    @DisplayName("Test Of StudentBag.drawStudent")
    public void emptyStudentBagTest(){
        int initialSize = studentBag.getStudentsInBag().size();
        for(int i=0; i<initialSize ; i++) {
            drawStudentTest();
        }
    }

    /**
     * This methodTest tests that an exception is thrown when drawStudent is called when the studentBag is empty
     */
    @Test
    public void StudentBagIsEmpty_TestException(){
        Assertions.assertThrows(IllegalArgumentException.class, ()->{
            int initializeSize = studentBag.getStudentsInBag().size();
            for(int i = 0 ; i<initializeSize + 1; i++){
                     studentBag.drawStudent();}
        });
    }

    /**
     * This methodTest tests the drawStudent method
     */
    @Test
   public void drawStudentTest(){
        int sizeBefore = studentBag.getStudentsInBag().size();
        Student student = studentBag.drawStudent();
        Assertions.assertEquals(sizeBefore -1, studentBag.getStudentsInBag().size());
        Color c = student.getColor();
        Assertions.assertTrue(studentBag.getNumberOfStudentsInBag(c) < 26);
        Assertions.assertFalse(studentBag.getStudentsInBag().contains(student));
   }


}
