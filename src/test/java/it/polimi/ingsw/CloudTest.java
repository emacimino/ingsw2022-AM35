package it.polimi.ingsw;

import it.polimi.ingsw.Charachter.Student;
import it.polimi.ingsw.Charachter.StudentBag;
import it.polimi.ingsw.Exception.ExceptionGame;
import it.polimi.ingsw.SchoolsLands.Cloud;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.Collection;

public class CloudTest {

    @ParameterizedTest
    @ValueSource(ints = {3,4})
    void setStudentOnCloud_Test(int numOfStudentOnCLoud){
        StudentBag studentBag = new StudentBag();
        Cloud cloud = new Cloud(numOfStudentOnCLoud);
        try{
            cloud.setStudentOnCloud(studentBag);
            Assertions.assertEquals(cloud.getNumOfStudentOnCloud(), numOfStudentOnCLoud);
        }catch(ExceptionGame e){
            Assertions.assertEquals(0, studentBag.getNumberOfStudents());
            System.out.println(e);
        }
        Assertions.assertThrows(ExceptionGame.class, () -> cloud.setStudentOnCloud(studentBag));

    }
    @ParameterizedTest
    @ValueSource(ints = {3,4})
    void removeStudentFromCloud_Test(int numOfStudentOnCLoud){
        StudentBag studentBag = new StudentBag();
        Cloud cloud = new Cloud(numOfStudentOnCLoud);
        try{
            cloud.setStudentOnCloud(studentBag);
            Collection<Student> copyStudents = new ArrayList<>();
            copyStudents.addAll(cloud.getStudentOnCloud());
            Collection<Student> students = cloud.removeStudentFromCloud();
            Assertions.assertEquals(0, cloud.getStudentOnCloud().size());
            Assertions.assertEquals(copyStudents, students);

        }catch(ExceptionGame e){
            Assertions.assertTrue(cloud.getStudentOnCloud().isEmpty());
            System.out.println(e);
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {3,4})
    void exceptionCloudEmpty_Test(int numOfStudentOnCLoud){
        Cloud cloud = new Cloud(numOfStudentOnCLoud);
        try{
            cloud.removeStudentFromCloud();
            Assertions.assertEquals(0, cloud.getStudentOnCloud().size());

        }catch(ExceptionGame e){
            Assertions.assertTrue(cloud.getStudentOnCloud().isEmpty());
            System.out.println(e);
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {3,4})
    void exceptionStudentBagEmpty_Test(int numOfStudentOnCLoud){
        StudentBag studentBag = new StudentBag();
        Cloud cloud = new Cloud(numOfStudentOnCLoud);
        while(studentBag.getNumberOfStudents()>0){
            try{
                cloud.setStudentOnCloud(studentBag);
                cloud.removeStudentFromCloud();
            }catch (ExceptionGame e){
                System.out.println(e);
            }
        }
        Assumptions.assumeTrue(studentBag.getStudentsInBag().isEmpty());
        Assertions.assertThrows(ExceptionGame.class, ()-> cloud.setStudentOnCloud(studentBag));
    }
}
