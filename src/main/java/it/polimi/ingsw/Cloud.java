package it.polimi.ingsw;

import java.util.ArrayList;
import java.util.Collection;

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
     * @return number of student on cloud
     */
    public int getNumOfStudentOnCloud() {
        return numOfStudentOnCloud;
    }

    /**
     * Put students randomly drawn from the student bag on the cloud
     * @param studentBag is the student bag of the game
     * @throws ExceptionGame the exception is thrown when the cloud is full or the student bag is empty
     */
    public void setStudentOnCloud(StudentBag studentBag) throws ExceptionGame{
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
     * @return collection of students on cloud
     */
    public Collection<Student> getStudentOnCloud() {
        return studentOnCloud;
    }

    /**
     * Remove the students from the clouds
     * @return the collection of students removed from the cloud
     * @throws ExceptionGame Exception is thrown when the cloud is already empty
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
