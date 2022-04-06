package it.polimi.ingsw.ModelTest.SchoolsLandsTest;

import it.polimi.ingsw.Model.SchoolsMembers.Student;
import it.polimi.ingsw.Model.SchoolsMembers.StudentBag;
import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.SchoolsLands.Cloud;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.Collection;

public class CloudTest {

    /**
     * This methodTest tests the setStudentOnCloud method
     * @param numOfStudentOnCLoud is the number of student allowed on the cloud
     */
    @ParameterizedTest
    @ValueSource(ints = {3,4})
    void setStudentOnCloud_Test(int numOfStudentOnCLoud){
        StudentBag studentBag = new StudentBag();
        Cloud cloud = new Cloud(numOfStudentOnCLoud);
        Assertions.assertDoesNotThrow(()-> {
            cloud.setStudentsOnCloud(studentBag);
            Assertions.assertEquals(cloud.getNumOfStudentOnCloud(), numOfStudentOnCLoud);
        });
        Assertions.assertThrows(ExceptionGame.class, () -> cloud.setStudentsOnCloud(studentBag));

    }

    /**
     * This methodTest tests the removeStudentFromCloud method
     * @param numOfStudentOnCLoud is the number of student allowed on the cloud
     */
    @ParameterizedTest
    @ValueSource(ints = {3,4})
    void removeStudentFromCloud_Test(int numOfStudentOnCLoud){
        StudentBag studentBag = new StudentBag();
        Cloud cloud = new Cloud(numOfStudentOnCLoud);
        Assertions.assertDoesNotThrow(()-> {
            cloud.setStudentsOnCloud(studentBag);
            Collection<Student> copyStudents = new ArrayList<>(cloud.getStudentOnCloud());
            Collection<Student> students = cloud.removeStudentFromCloud();
            Assertions.assertEquals(0, cloud.getStudentOnCloud().size());
            Assertions.assertEquals(copyStudents, students);

        });
    }

    /**
     * This methodTest tests that the removeStudentFromCloud method throws an exception
     * when the cloud is already empty
     * @param numOfStudentOnCLoud is the number of student allowed on the cloud
     */
    @ParameterizedTest
    @ValueSource(ints = {3,4})
    void exceptionCloudEmpty_Test(int numOfStudentOnCLoud){
        Cloud cloud = new Cloud(numOfStudentOnCLoud);
        Assertions.assertThrows(ExceptionGame.class, ()-> {
            cloud.removeStudentFromCloud();
            Assertions.assertEquals(0, cloud.getStudentOnCloud().size());
        });
    }

    /**
     * This methodTest tests that when the studentBag is empty, an exception is thrown
     * @param numOfStudentOnCLoud is the number of student allowed on the cloud
     */
    @ParameterizedTest
    @ValueSource(ints = {3,4})
    void exceptionStudentBagEmpty_Test(int numOfStudentOnCLoud){
        StudentBag studentBag = new StudentBag();
        Cloud cloud = new Cloud(numOfStudentOnCLoud);
        while(studentBag.getNumberOfStudents()>numOfStudentOnCLoud){
            Assertions.assertDoesNotThrow(()-> {
                cloud.setStudentsOnCloud(studentBag);
                cloud.removeStudentFromCloud();
            });
        }
        Assumptions.assumeTrue(studentBag.getStudentsInBag().size() < numOfStudentOnCLoud);
        Assertions.assertThrows(ExceptionGame.class, ()-> cloud.setStudentsOnCloud(studentBag));
    }
}
