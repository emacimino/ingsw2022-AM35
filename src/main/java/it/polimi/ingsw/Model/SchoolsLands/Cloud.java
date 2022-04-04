package it.polimi.ingsw.Model.SchoolsLands;

import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.SchoolsMembers.Student;
import it.polimi.ingsw.Model.SchoolsMembers.StudentBag;

import java.util.ArrayList;
import java.util.Collection;

/**
 * This class represents the Cloud Card of the Game
 */
public class Cloud {
    private final int numOfStudentOnCloud;
    private final Collection<Student> studentOnCloud = new ArrayList<>();

    /**
     * constructor of Cloud Class
     * @param numOfStudentOnCloud is the number of student allowed on the Cloud
     */
    public Cloud(int numOfStudentOnCloud) {
        this.numOfStudentOnCloud = numOfStudentOnCloud;
    }

    /**
     * returns the number of student allowed on the cloud
     * @return number of student on cloud
     */
    public int getNumOfStudentOnCloud() {
        return numOfStudentOnCloud;
    }

    /**
     * Put students randomly drawn from the student bag on the cloud, if the cloud is full the method throws an exception
     * @param studentBag is the student bag of the game
     * @throws ExceptionGame the exception is thrown when the cloud is full or the student bag is empty
     */
    public void setStudentsOnCloud(StudentBag studentBag) throws ExceptionGame{
        if(!studentOnCloud.isEmpty())
            throw new ExceptionGame("The Cloud is full");
        else{
            for( int i = 0; i<numOfStudentOnCloud; i++) {
                try {
                    Student s = studentBag.drawStudent();
                    studentOnCloud.add(s);
                }catch(IllegalArgumentException e){
                    throw new ExceptionGame("The student bag is empty", e);
                }
            }
        }
    }

    /**
     * This method returns the students on the cloud
     * @return collection of students on cloud
     */
    public Collection<Student> getStudentOnCloud() {
        return studentOnCloud;
    }

    /**
     * Remove the students from the clouds, if the cloud is already empty, the method throws an exception
     * @return the collection of students removed from the cloud
     * @throws ExceptionGame is thrown when the cloud is already empty
     */
    public Collection<Student> removeStudentFromCloud() throws ExceptionGame{
        if(studentOnCloud.isEmpty())
            throw new ExceptionGame("The Cloud is already empty");
        else {
            Collection<Student> returnStudents = new ArrayList<>(studentOnCloud);
            studentOnCloud.removeAll(returnStudents);
            return returnStudents;
        }
    }
}
