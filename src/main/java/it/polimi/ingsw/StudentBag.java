package it.polimi.ingsw;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StudentBag {

    private final List<Student> studentsInBag;

    /**
     * StudentBag is the construct of the Class StudentBag which set 26 Student
     * for each value present in the Class Color
     */

    public StudentBag(){
        studentsInBag = new ArrayList<>();
        for (Color c: Color.values()) {
            for(int i = 0; i<26; i++) {
                Student student = new Student(c);
                studentsInBag.add(student);
            }
        }
    }

    /**
     *
     * @return the size of the attribute studentInBag
     */
    public int getNumberOfStudents(){
        return studentsInBag.size();
    }

    /**
     * @return the Student randomly removed in the method drawStudent()
     * @throws IllegalArgumentException Exception is thrown when there are no more student to remove from StudentBag
     */
    public Student drawStudent() throws IllegalArgumentException {
        Random random = new Random();
            return studentsInBag.remove(random.nextInt(studentsInBag.size()));

    }

    /**
     * @return reference of the attribute studentsInBag
     */
    public List<Student> getStudentsInBag() {
        return studentsInBag;
    }

    /**
     * @param c is the Color of interest
     * @return the number of Student whose attribute color is c
     */
    public int getNumberOfStudentsInBag(Color c) {
        int count = 0;
        for (Student student : studentsInBag) {
            if (student.getColor() == c) {
                count++;
            }
        }
        return count;
    }

    @Override
    public String toString() {
        return "StudentBag:" + "\n" +"Blue Students = " + getNumberOfStudentsInBag(Color.BLUE)+
                "\n" + "Green Students = " + getNumberOfStudentsInBag(Color.GREEN) +
                "\n" + "Pink Students = " + getNumberOfStudentsInBag(Color.PINK) +
                "\n" + "Red Students = " + getNumberOfStudentsInBag(Color.RED) +
                "\n" + "Yellow Students = " + getNumberOfStudentsInBag(Color.YELLOW) +
                "\n" + "total Students = " + studentsInBag.size() +
                "\n";
    }
}
